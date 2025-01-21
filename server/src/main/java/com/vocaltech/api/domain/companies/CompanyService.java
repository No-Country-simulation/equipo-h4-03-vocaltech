package com.vocaltech.api.domain.companies;

import com.vocaltech.api.domain.entrepreneurs.Entrepreneur;
import com.vocaltech.api.domain.entrepreneurs.EntrepreneurRequestDTO;
import com.vocaltech.api.domain.products.IProductRepository;
import com.vocaltech.api.domain.products.Product;
import com.vocaltech.api.domain.products.ProductEnum;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Transient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    private final ICompanyRepository companyRepository;
    private final IProductRepository productRepository;

    public CompanyService(ICompanyRepository companyRepository, IProductRepository productRepository) {
        this.companyRepository = companyRepository;
        this.productRepository = productRepository;
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


    public Company createCompany(CompanyRequestDTO requestDTO) {

        Company company = new Company();

        company.setCompanyName(requestDTO.companyName());
        company.setSector(requestDTO.sector());
        company.setSize(requestDTO.size());
        company.setContactName(requestDTO.contactName());
        company.setContactEmail(requestDTO.email());
        company.setContactPhone(requestDTO.phone());
        company.setDescription(requestDTO.description());
        company.setMVP(requestDTO.MVP());
        company.setDevelopmentStage(requestDTO.developmentStage());
        company.setHireJunior(requestDTO.hireJunior());
        company.setTalentProfile(requestDTO.talentProfile());
        company.setMoreInfo(requestDTO.moreInfo());
        company.setActive(true);

        Set<Product> products = getProductsFromNames(requestDTO.products());

        // Convertir Set a List antes de asignarlo
        company.setProducts(new ArrayList<>(products));

        // Guardar el emprendedor
        return companyRepository.save(company);
    }


    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public List<Company> getActiveCompanies() {
        return companyRepository.findByActiveTrue();  // Supone que tienes una consulta específica en el repositorio
    }

    public Company getCompanyById(UUID id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
    }

    @Transient
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
        if (requestDTO.contactName() != null && !requestDTO.contactName().isBlank()) {
            company.setContactName(requestDTO.contactName());
        }
        if (requestDTO.email() != null && !requestDTO.email().isBlank()) {
            company.setContactEmail(requestDTO.email());
        }
        if (requestDTO.phone() != null && !requestDTO.phone().isBlank()) {
            company.setContactPhone(requestDTO.phone());
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

    @Transient
    public void deleteCompany(UUID id) {
        Company company = getCompanyById(id);
        if (!company.getActive()) {
            throw new IllegalStateException("Company is already inactive.");
        }
        company.setActive(false);
        companyRepository.save(company); // Persistimos el cambio
    }
}
