package com.vocaltech.api.domain.leads;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Transient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LeadService {

    private final ILeadRepository leadRepository;

    public LeadService(ILeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    public Lead createLead(LeadRequestDTO requestDTO) {
        Lead lead = new Lead();
        lead.setName(requestDTO.name());
        lead.setEmail(requestDTO.email());
        lead.setSubscribed(
                requestDTO.subscribed() != null ? requestDTO.subscribed() : true // Valor predeterminado
        );

        return leadRepository.save(lead);
    }

    public List<Lead> getAllLeads() {
        return leadRepository.findAll();
    }

    public List<Lead> getSubscribedLeads() {
        // Devuelve sólo los leads activos
        return leadRepository.findBySubscribedTrue();
    }

    public Lead getLeadById(UUID id) {
        return leadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lead not found with ID: " + id));
    }

    @Transient
    public void unsubscribe(UUID id) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lead not found"));

        lead.setSubscribed(false);
        leadRepository.save(lead);
    }

    public void deleteLead(UUID id) {
        Lead lead = getLeadById(id);

        if (!lead.getSubscribed()) {
            throw new IllegalStateException("Lead is already unsubscribed.");
        }

        // Actualizamos el estado activo a false
        lead.setSubscribed(false);
        leadRepository.save(lead);
    }
}
