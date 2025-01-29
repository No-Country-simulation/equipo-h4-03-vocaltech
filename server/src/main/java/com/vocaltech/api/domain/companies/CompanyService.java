package com.vocaltech.api.domain.companies;


import com.vocaltech.api.domain.entrepreneurs.IEntrepreneurRepository;
import com.vocaltech.api.domain.leads.ILeadRepository;
import com.vocaltech.api.domain.products.IProductRepository;
import com.vocaltech.api.domain.products.Product;
import com.vocaltech.api.domain.products.ProductEnum;
import com.vocaltech.api.service.S3Service;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    private final ICompanyRepository companyRepository;
    private final IProductRepository productRepository;
    private final ILeadRepository leadRepository;
    private final IEntrepreneurRepository entrepreneurRepository;
    private final S3Service s3Service;

    @Autowired
    public CompanyService(ICompanyRepository companyRepository, IProductRepository productRepository, ILeadRepository leadRepository, IEntrepreneurRepository entrepreneurRepository, S3Service s3Service) {
        this.companyRepository = companyRepository;
        this.productRepository = productRepository;
        this.leadRepository = leadRepository;
        this.entrepreneurRepository = entrepreneurRepository;
        this.s3Service = s3Service;
    }

    public Set<Product> getProductsFromNames(List<String> productNames) {
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


    @Transactional
    public Company createCompany(
            CompanyRequestDTO requestDTO,
            InputStream audioInputStream,
            String audioFilename
    ) {


        String audioFileName = requestDTO.companyName() +"-"+ audioFilename;
        // Subir archivos a S3
        String audioUrl = s3Service.uploadFile("audios/", audioFileName , audioInputStream, "audio/mpeg");

        Company company = new Company();

        company.setCompanyName(requestDTO.companyName());
        company.setSector(requestDTO.sector());
        company.setSize(requestDTO.size());

        company.setDescription(requestDTO.description());
        company.setMVP(requestDTO.MVP());
        company.setDevelopmentStage(requestDTO.developmentStage());
        company.setHireJunior(requestDTO.hireJunior());
        company.setTalentProfile(requestDTO.talentProfile());
        company.setMoreInfo(requestDTO.moreInfo());


        Set<Product> products = getProductsFromNames(requestDTO.products());

        // Convertir Set a List antes de asignarlo
        company.setProducts(new ArrayList<>(products));

        // Asignar las URLs de los archivos
        company.setAudioKey(audioFileName);

        return companyRepository.save(company);
    }


    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public List<Company> getActiveCompanies() {
        return companyRepository.findBySubscribedTrue();  // Supone que tienes una consulta específica en el repositorio
    }

    public Company getCompanyById(UUID id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
    }

    @Transactional
    public Company updateCompany(UUID id, CompanyRequestDTO requestDTO) {
        Company company = getCompanyById(id);

        // Validación condicional para actualizar solo los campos proporcionados
        if (requestDTO.companyName() != null && !requestDTO.companyName().isBlank()) {
            company.setCompanyName(requestDTO.companyName());
        }

        if (requestDTO.sector() != null && !requestDTO.sector().isBlank()) {
            company.setSector(requestDTO.sector());
        }
        if (requestDTO.size() != null) {
            company.setSize(requestDTO.size());
        }

        if (requestDTO.description() != null) {
            company.setDescription(requestDTO.description());
        }
        if (requestDTO.MVP() != null) {
            company.setMVP(requestDTO.MVP());
        }
        if (requestDTO.developmentStage() != null) {
            company.setDevelopmentStage(requestDTO.developmentStage());
        }
        if (requestDTO.hireJunior() != null) {
            company.setHireJunior(requestDTO.hireJunior());
        }
        if (requestDTO.talentProfile() != null) {
            company.setTalentProfile(requestDTO.talentProfile());
        }
        if (requestDTO.moreInfo() != null) {
            company.setMoreInfo(requestDTO.moreInfo());
        }

        if (requestDTO.products() != null && !requestDTO.products().isEmpty()) {

            Set<Product> products = getProductsFromNames(requestDTO.products());

            // Convertir Set a List antes de asignarlo
            company.setProducts(new ArrayList<>(products));
        }

        return companyRepository.save(company);
    }

    @Transactional
    public void unsubscribe(UUID id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lead not found"));

        company.setSubscribed(false);
        companyRepository.save(company);
    }

    @Transactional
    public void deleteCompany(UUID id) {
        Company company = getCompanyById(id);
        if (!company.getSubscribed()) {
            throw new IllegalStateException("Company is already inactive.");
        }
        company.setSubscribed(false);
        companyRepository.save(company); // Persistimos el cambio
    }
}
