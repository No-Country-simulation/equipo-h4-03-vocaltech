package com.vocaltech.api.service;

import com.vocaltech.api.dto.request.entrepeneur.EntrepreneurRequestDTO;
import com.vocaltech.api.model.Entrepreneur;
import com.vocaltech.api.repository.EntrepreneurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EntrepreneurService {

    private final EntrepreneurRepository entrepreneurRepository;

    public EntrepreneurService(EntrepreneurRepository entrepreneurRepository) {
        this.entrepreneurRepository = entrepreneurRepository;
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
