package com.vocaltech.api.domain.entrepreneurs;

import com.vocaltech.api.domain.products.IProductRepository;
import com.vocaltech.api.domain.products.Product;
import com.vocaltech.api.domain.products.ProductEnum;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EntrepreneurService {

    private final IEntrepreneurRepository entrepreneurRepository;
    private final IProductRepository productRepository;

    public EntrepreneurService(IEntrepreneurRepository entrepreneurRepository, IProductRepository productRepository) {
        this.entrepreneurRepository = entrepreneurRepository;
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


    public Entrepreneur createEntrepreneur(EntrepreneurRequestDTO requestDTO) {
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

        Set<Product> products = getProductsFromNames(requestDTO.products());

       // Convertir Set a List antes de asignarlo
        entrepreneur.setProducts(new ArrayList<>(products));

        // Guardar el emprendedor
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


    public void deleteEntrepreneur(UUID id) {
        Entrepreneur entrepreneur = getEntrepreneurById(id);
        if (!entrepreneur.getActive()) {
            throw new IllegalStateException("Entrepreneur is already inactive.");
        }
        entrepreneur.setActive(false);
        entrepreneurRepository.save(entrepreneur); // Persistimos el cambio
    }
}
