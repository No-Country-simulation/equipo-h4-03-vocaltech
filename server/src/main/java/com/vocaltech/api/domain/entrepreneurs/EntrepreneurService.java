package com.vocaltech.api.domain.entrepreneurs;

import com.google.zxing.WriterException;
import com.vocaltech.api.domain.companies.ICompanyRepository;
import com.vocaltech.api.domain.leads.ILeadRepository;
import com.vocaltech.api.domain.leads.Lead;
import com.vocaltech.api.domain.products.IProductRepository;
import com.vocaltech.api.domain.products.Product;
import com.vocaltech.api.domain.products.ProductEnum;
import com.vocaltech.api.domain.recipients.IRecipientRepository;
import com.vocaltech.api.service.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EntrepreneurService {

    private final IEntrepreneurRepository entrepreneurRepository;
    private final IProductRepository productRepository;
    private final ILeadRepository leadRepository;
    private final ICompanyRepository companyRepository;
    private final S3Service s3Service;
    private final ChatAnalysisService chatAnalysisService;
    private final PdfGeneratorService pdfGeneratorService;
    private final QRCodeService qrCodeService;
    private final TranscriptionService transcriptionService;
    private final MailServices mailServices;

    @Autowired
    public EntrepreneurService(IEntrepreneurRepository entrepreneurRepository, IProductRepository productRepository, ILeadRepository leadRepository, ILeadRepository leadRepository1, ICompanyRepository companyRepository, com.vocaltech.api.service.S3Service s3Service, IRecipientRepository recipientRepository, S3Service s3Service1, ChatAnalysisService chatAnalysisService, PdfGeneratorService pdfGeneratorService, QRCodeService qrCodeService, TranscriptionService transcriptionService, MailServices mailServices) {
        this.entrepreneurRepository = entrepreneurRepository;
        this.productRepository = productRepository;
        this.leadRepository = leadRepository1;
        this.companyRepository = companyRepository;
        this.s3Service = s3Service1;
        this.chatAnalysisService = chatAnalysisService;
        this.pdfGeneratorService = pdfGeneratorService;
        this.qrCodeService = qrCodeService;
        this.transcriptionService = transcriptionService;
        this.mailServices = mailServices;
    }


    private Set<Product> getProductsFromNames(List<String> productNames) {
        // Convertir nombres a IDs usando ProductEnum y cargar los servicios desde la base de datos
        Set<UUID> productsIds = productNames.stream()
                .map(ProductEnum::fromName)  // Convierte el nombre al enum
                .map(ProductEnum::getId)     // Obtiene el ID del enum
                .collect(Collectors.toSet());

        // Cargar los productos desde la base de datos utilizando los IDs
        Set<Product> products = new HashSet<>(productRepository.findAllById(productsIds));

        // Verificar si todos los productos fueron encontrados
        if (products.size() != productsIds.size()) {
            throw new EntityNotFoundException("Algunos servicios no se encontraron en la base de datos.");
        }

        return products;
    }

    private void deleteTemporaryFiles(File pdfFile, File qrFile) {
        if (!pdfFile.delete()) {
            System.out.println("No se pudo eliminar el archivo PDF: " + pdfFile.getAbsolutePath());
        } else {
            System.out.println("Archivo PDF eliminado correctamente.");
        }

        if (!qrFile.delete()) {
            System.out.println("No se pudo eliminar el archivo QR: " + qrFile.getAbsolutePath());
        } else {
            System.out.println("Archivo QR eliminado correctamente.");
        }
    }

    private File generateAndUploadPdf(String name, String analysis, byte[] qrBytes, String pdfFilename) throws IOException {
        String pdfPath = "src/main/java/com/vocaltech/api/tmp/" + pdfFilename;
        pdfGeneratorService.generatePdf(pdfPath, analysis, name, qrBytes);
        File pdfFile = new File(pdfPath);
        if (!pdfFile.exists() || pdfFile.length() == 0) {
            throw new RuntimeException("El archivo PDF no se generó correctamente");
        }
        s3Service.uploadFile("diagnostics/", pdfFilename, new FileInputStream(pdfFile), "application/pdf");
        return pdfFile;
    }

    private void sendDiagnosticEmail(String email, String analysis, File pdfFile, byte[] qrBytes, String qrFilename) {
        // Crear archivo temporal para el QR
        File qrFile = new File("src/main/java/com/vocaltech/api/tmp/" + qrFilename);

        try (FileOutputStream fos = new FileOutputStream(qrFile)) {
            fos.write(qrBytes);


            // Mensaje del correo
            String message = "Hola,\n\n" +
                    "Gracias por completar el diagnóstico. Aquí tienes los resultados adjuntos:\n\n" +
                    "Análisis: " + analysis + "\n\n" +
                    "Saludos,\nEl equipo de VocalTech";

            // Crear la lista de archivos adjuntos
            List<File> attachments = List.of(pdfFile, qrFile);

            // Enviar correo
            mailServices.sendEmailWithFiles(
                    email,
                    "Diagnóstico Completo con Adjuntos",
                    message,
                    attachments
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to create temporary QR code file", e);
        } finally {
            deleteTemporaryFiles(pdfFile, qrFile);

        }
    }

    private Entrepreneur handleEntrepreneurConversion(
            EntrepreneurRequestDTO requestDTO,
            String audioFileName,
            String transcription,
            String analysis,
            String pdfFileName,
            String qrFileName

    ) {
        Optional<Lead> leadOptional = leadRepository.findByEmail(requestDTO.email());

    Entrepreneur entrepreneur;

    if (leadOptional.isPresent()) {
        // Si es un Lead, convertimos el Lead en Entrepreneur
        Lead lead = leadOptional.get();

        // Convertir el Lead en Entrepreneur
        entrepreneur = new Entrepreneur();
        entrepreneur.setName(lead.getName());
        entrepreneur.setEmail(lead.getEmail());
        entrepreneur.setSubscribed(true); // Marcar como suscrito, por ejemplo

        // Otros campos específicos de Entrepreneur, como el teléfono, descripción, etc.
        entrepreneur.setPhone(requestDTO.phone());
        entrepreneur.setType(requestDTO.type());
        entrepreneur.setDescription(requestDTO.description());
        entrepreneur.setMVP(requestDTO.MVP());
        entrepreneur.setProductToDevelop(requestDTO.productToDevelop());
        entrepreneur.setHireJunior(requestDTO.hireJunior());
        entrepreneur.setMoreInfo(requestDTO.moreInfo());

        // Convertir productos (si es necesario)
        Set<Product> products = getProductsFromNames(requestDTO.products());
        entrepreneur.setProducts(new ArrayList<>(products));

        entrepreneur.setAudioKey(audioFileName);
        entrepreneur.setTranscription(transcription);
        entrepreneur.setAnalysis(analysis);
        entrepreneur.setDiagnosisPdfKey(pdfFileName);
        entrepreneur.setQrCodeKey(qrFileName);

        // Guardar o actualizar en la base de datos
        return entrepreneur;
    } else {
        // Si no es un Lead, buscamos si ya es un Entrepreneur

        Entrepreneur existingEntrepreneur = entrepreneurRepository.findByEmail(requestDTO.email())
                .orElseGet(() -> {
                    // Si no se encuentra, creamos un nuevo Entrepreneur
                    Entrepreneur newEntrepreneur = new Entrepreneur();
                    newEntrepreneur.setEmail(requestDTO.email());
                    newEntrepreneur.setName(requestDTO.name());
                    newEntrepreneur.setSubscribed(true); // Marcar como suscrito, por ejemplo

                    // Otros campos específicos de Entrepreneur, como el teléfono, descripción, etc.
                    newEntrepreneur.setPhone(requestDTO.phone());
                    newEntrepreneur.setType(requestDTO.type());
                    newEntrepreneur.setDescription(requestDTO.description());
                    newEntrepreneur.setMVP(requestDTO.MVP());
                    newEntrepreneur.setProductToDevelop(requestDTO.productToDevelop());
                    newEntrepreneur.setHireJunior(requestDTO.hireJunior());
                    newEntrepreneur.setMoreInfo(requestDTO.moreInfo());

                    // Convertir productos (si es necesario)
                    Set<Product> products = getProductsFromNames(requestDTO.products());
                    newEntrepreneur.setProducts(new ArrayList<>(products));


                    newEntrepreneur.setAudioKey(audioFileName);
                    newEntrepreneur.setTranscription(transcription);
                    newEntrepreneur.setAnalysis(analysis);
                    newEntrepreneur.setDiagnosisPdfKey(pdfFileName);
                    newEntrepreneur.setQrCodeKey(qrFileName);

                    return newEntrepreneur;
                });

        // Si ya existe un Entrepreneur, realizamos la actualización, si no, guardamos el nuevo.
        if (existingEntrepreneur.getRecipientId() != null) {
            // Ya existe, lo actualizamos
            return updateEntrepreneur(existingEntrepreneur.getRecipientId(), requestDTO);
        } else {
            // Nuevo Entrepreneur, lo guardamos
            return existingEntrepreneur;
        }
    }
    }





    @Transactional
    public Entrepreneur createEntrepreneur(
            EntrepreneurRequestDTO requestDTO,
            InputStream audioInputStream,
            Resource audioResource,
            String audioFilename
    ) throws IOException, WriterException {



        String audioFileName = requestDTO.name() +"-"+ audioFilename;
        // Subir archivos a S3
        String audioUrl = s3Service.uploadFile("audios/", audioFileName, audioInputStream, "audio/mpeg");

        // Transcribir audio (utiliza un servicio de transcripción)
        //String transcription = transcriptionService.transcribeAudio(audioResource);
        String transcription = """
                Hola, soy María Pérez, fundadora de TechSolutions. 
                En nuestro equipo, uno de los principales desafíos que enfrentamos es la falta 
                de comunicación clara y eficiente entre los miembros, especialmente cuando trabajamos 
                en proyectos tecnológicos complejos. Suele pasar que la información no fluye de manera adecuada, 
                lo que puede llevar a malentendidos, algunos retrasos en la toma de decisiones. Esto termina afectando 
                tanto a la productividad como a la calidad del trabajo. Estamos necesitando herramientas y procesos 
                que mejoren la colaboración y aseguren que todos estemos alineados para lograr resultados 
                más rápidos y efectivos.""";

        // Analizar transcripción
        //String analysis = chatAnalysisService.analyzeTranscription(transcription);
        String analysis ="En equipos tecnológicos como el tuyo, una comunicación poco clara puede traducirse en malentendidos, retrasos y pérdida de eficiencia. Si la información no fluye de manera estructurada, las decisiones se dilatan y la calidad del trabajo se ve afectada. Esto no solo impacta la productividad, sino que también genera frustración y desgaste en el equipo.\n" +
                "\n" +
                "La buena noticia es que esto tiene solución. Con las herramientas y metodologías adecuadas, tu equipo puede lograr una comunicación más ágil, precisa y alineada con sus objetivos. Desde estrategias efectivas para reuniones hasta la optimización del uso de herramientas digitales, podemos ayudarte a transformar la manera en que tu equipo se comunica, asegurando mejores resultados en menos tiempo. ";

        String pdfFilename = requestDTO.name() + "-diagnosis.pdf";

        // Generar código QR
        String qrContent = "https://vocaltech-test.vercel.app/"; // URL del diagnóstico
        byte[] qrBytes = qrCodeService.generateQRCode(qrContent);
        String qrFilename = requestDTO.name() + "-qr.png";
        String qrUrl = s3Service.uploadFile("qrcodes/", qrFilename, new ByteArrayInputStream(qrBytes), "image/png");

        File pdfFile = generateAndUploadPdf(requestDTO.name(), analysis, qrBytes, pdfFilename);

        Entrepreneur entrepreneur = handleEntrepreneurConversion(requestDTO, audioFileName, transcription, analysis, pdfFilename, qrFilename);

        // Enviar email con los archivos adjuntos
        sendDiagnosticEmail(requestDTO.email(), analysis, pdfFile, qrBytes, qrFilename);

// Guardar el entrepreneur
        return entrepreneurRepository.save(entrepreneur);


    }



    public List<Entrepreneur> getAllEntrepreneurs() {
        return entrepreneurRepository.findAll();
    }

    public List<Entrepreneur> getActiveEntrepreneurs() {
        return entrepreneurRepository.findBySubscribedTrue();  // Supone que tienes una consulta específica en el repositorio
    }

    public Entrepreneur getEntrepreneurById(UUID id) {
        return entrepreneurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrepreneur not found with id: " + id));
    }

    @Transactional
    public Entrepreneur updateEntrepreneur(UUID id, EntrepreneurRequestDTO requestDTO) {
        Entrepreneur entrepreneur = getEntrepreneurById(id);

        // Validación condicional para actualizar solo los campos proporcionados
        if (requestDTO.name() != null && !requestDTO.name().isBlank()) {
            entrepreneur.setName(requestDTO.name());
        }
        if (requestDTO.email() != null && !requestDTO.email().isBlank()) {
            entrepreneur.setEmail(requestDTO.email());
        }
        if (requestDTO.phone() != null && !requestDTO.phone().isBlank()) {
            entrepreneur.setPhone(requestDTO.phone());
        }
        if (requestDTO.type() != null) {
            entrepreneur.setType(requestDTO.type());
        }
        if (requestDTO.description() != null) {
            entrepreneur.setDescription(requestDTO.description());
        }
        if (requestDTO.MVP() != null) {
            entrepreneur.setMVP(requestDTO.MVP());
        }
        if (requestDTO.productToDevelop() != null) {
            entrepreneur.setProductToDevelop(requestDTO.productToDevelop());
        }
        if (requestDTO.hireJunior() != null) {
            entrepreneur.setHireJunior(requestDTO.hireJunior());
        }
        if (requestDTO.moreInfo() != null) {
            entrepreneur.setMoreInfo(requestDTO.moreInfo());
        }

        if (requestDTO.products() != null && !requestDTO.products().isEmpty()) {

            Set<Product> products = getProductsFromNames(requestDTO.products());

            // Convertir Set a List antes de asignarlo
            entrepreneur.setProducts(new ArrayList<>(products));
        }

        return entrepreneurRepository.save(entrepreneur);
    }

    @Transactional
    public void unsubscribe(UUID id) {
        Entrepreneur entrepreneur = entrepreneurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lead not found"));

        entrepreneur.setSubscribed(false);
        entrepreneurRepository.save(entrepreneur);
    }


    @Transactional
    public void deleteEntrepreneur(UUID id) {
        Entrepreneur entrepreneur = getEntrepreneurById(id);
        if (!entrepreneur.getSubscribed()) {
            throw new IllegalStateException("Entrepreneur is already desubscripto.");
        }
        entrepreneur.setSubscribed(false);
        entrepreneurRepository.save(entrepreneur); // Persistimos el cambio
    }
}
