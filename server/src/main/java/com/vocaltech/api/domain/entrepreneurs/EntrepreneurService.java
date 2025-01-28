package com.vocaltech.api.domain.entrepreneurs;

import com.google.zxing.WriterException;
import com.vocaltech.api.domain.companies.ICompanyRepository;
import com.vocaltech.api.domain.leads.ILeadRepository;
import com.vocaltech.api.domain.leads.Lead;
import com.vocaltech.api.domain.products.IProductRepository;
import com.vocaltech.api.domain.products.Product;
import com.vocaltech.api.domain.products.ProductEnum;
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
    public EntrepreneurService(IEntrepreneurRepository entrepreneurRepository, IProductRepository productRepository, ILeadRepository leadRepository, ICompanyRepository companyRepository, com.vocaltech.api.service.S3Service s3Service, S3Service s3Service1, ChatAnalysisService chatAnalysisService, PdfGeneratorService pdfGeneratorService, QRCodeService qrCodeService, TranscriptionService transcriptionService, MailServices mailServices) {
        this.entrepreneurRepository = entrepreneurRepository;
        this.productRepository = productRepository;
        this.leadRepository = leadRepository;
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

            // Eliminar archivos temporales
            // Eliminar el archivo PDF
            if (!pdfFile.delete()) {
                // Si el archivo no se puede eliminar, loguea un mensaje de advertencia
                System.out.println("No se pudo eliminar el archivo PDF: " + pdfFile.getAbsolutePath());
            } else {
                System.out.println("Archivo PDF eliminado correctamente.");
            }


// Eliminar el archivo QR
            if (!qrFile.delete()) {
                // Si el archivo no se puede eliminar, loguea un mensaje de advertencia
                System.out.println("No se pudo eliminar el archivo QR: " + qrFile.getAbsolutePath());
            } else {
                System.out.println("Archivo QR eliminado correctamente.");
            }
        }
    }


    @Transactional
    public Entrepreneur createEntrepreneur(
            EntrepreneurRequestDTO requestDTO,
            UUID leadId,
            InputStream audioInputStream,
            Resource audioResource,
            String audioFilename
    ) throws IOException, WriterException {
        Lead lead = null;

        if (leadId != null) {
            lead = leadRepository.findById(leadId)
                    .orElseThrow(() -> new EntityNotFoundException("Lead not found"));

            if (companyRepository.existsByLeadLeadId(leadId)) {
                throw new IllegalStateException("This lead is already associated as a Company");
            }

            if (!lead.getSubscribed()) {
                throw new IllegalStateException("Lead is already unsubscribed");
            }

            lead.setSubscribed(false);
            leadRepository.save(lead);
        }

        // Subir archivos a S3
        String audioUrl = s3Service.uploadFile("audios/", requestDTO.name() +"-"+ audioFilename, audioInputStream, "audio/mpeg");


        // Transcribir audio (utiliza un servicio de transcripción)
        //String transcription = transcriptionService.transcribeAudio(audioResource);
        String transcription ="No funciona OpenAI";


                // Analizar transcripción
        //String analysis = chatAnalysisService.analyzeTranscription(transcription);
        String analysis ="Esta es una prueba porque OpenAI no responde aún, esperemos que pronto este solucionado como asi tambien todos los problemas de comunicacion de vocaltech h4-3";

        String pdfFilename = requestDTO.name() + "-diagnosis.pdf";

        // Generar código QR
        String qrContent = "https://vocaltech.bucket.s3.amazonaws.com/diagnostics/" + pdfFilename; // URL del diagnóstico
        byte[] qrBytes = qrCodeService.generateQRCode(qrContent);
        String qrFilename = requestDTO.name() + "-qr.png";
        String qrUrl = s3Service.uploadFile("qrcodes/", qrFilename, new ByteArrayInputStream(qrBytes), "image/png");

        // Crear PDF
        String pdfPath = "src/main/java/com/vocaltech/api/tmp/" + pdfFilename; // Ruta temporal para crear el PDF

        try {
            pdfGeneratorService.generatePdf(pdfPath, analysis, requestDTO.name(), qrBytes);
        } catch (IOException e) {
            throw new RuntimeException("Error al generar el PDF", e);
        }

        // Subir PDF a S3
        File pdfFile = new File(pdfPath);
        if (!pdfFile.exists() || pdfFile.length() == 0) {
            throw new RuntimeException("El archivo PDF no se generó correctamente");
        }
        String pdfUrl = s3Service.uploadFile("diagnostics/", pdfFilename, new FileInputStream(pdfFile), "application/pdf");


        Entrepreneur entrepreneur = new Entrepreneur();
        entrepreneur.setName(requestDTO.name());
        entrepreneur.setEmail(requestDTO.email());
        entrepreneur.setPhone(requestDTO.phone());
        entrepreneur.setType(requestDTO.type());
        entrepreneur.setDescription(requestDTO.description());
        entrepreneur.setMVP(requestDTO.MVP());
        entrepreneur.setProductToDevelop(requestDTO.productToDevelop());
        entrepreneur.setHireJunior(requestDTO.hireJunior());
        entrepreneur.setMoreInfo(requestDTO.moreInfo());
        entrepreneur.setActive(true);
        entrepreneur.setLead(lead);

        Set<Product> products = getProductsFromNames(requestDTO.products());
        entrepreneur.setProducts(new ArrayList<>(products));

        // Asignar las URLs de los archivos
        entrepreneur.setAudioUrl(audioUrl);
        entrepreneur.setTranscription(transcription);
        entrepreneur.setAnalysis(analysis);
        entrepreneur.setDiagnosisPdfUrl(pdfUrl);
        entrepreneur.setQrCodeUrl(qrUrl);

        // **Enviar correo con los archivos adjuntos**
        sendDiagnosticEmail(requestDTO.email(), analysis, pdfFile, qrBytes, qrFilename);

        return entrepreneurRepository.save(entrepreneur);
    }




    public List<Entrepreneur> getAllEntrepreneurs() {
        return entrepreneurRepository.findAll();
    }

    public List<Entrepreneur> getActiveEntrepreneurs() {
        return entrepreneurRepository.findByActiveTrue();  // Supone que tienes una consulta específica en el repositorio
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

        entrepreneur.setActive(false);
        entrepreneurRepository.save(entrepreneur);
    }


    @Transactional
    public void deleteEntrepreneur(UUID id) {
        Entrepreneur entrepreneur = getEntrepreneurById(id);
        if (!entrepreneur.getActive()) {
            throw new IllegalStateException("Entrepreneur is already inactive.");
        }
        entrepreneur.setActive(false);
        entrepreneurRepository.save(entrepreneur); // Persistimos el cambio
    }
}
