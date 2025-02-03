package com.vocaltech.api.domain.leads;

import com.vocaltech.api.domain.campaigns.Campaign;
import com.vocaltech.api.domain.campaigns.CampaignRecipient;
import com.vocaltech.api.domain.campaigns.ICampaignRecipientRepository;
import com.vocaltech.api.domain.campaigns.ICampaignRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class LeadService {

    private final ILeadRepository leadRepository;
    private final ICampaignRepository campaignRepository;
    private final ICampaignRecipientRepository campaignRecipientRepository;

    @Autowired
    public LeadService(ILeadRepository leadRepository, ICampaignRepository campaignRepository, ICampaignRecipientRepository campaignRecipientRepository) {
        this.leadRepository = leadRepository;
        this.campaignRepository = campaignRepository;
        this.campaignRecipientRepository = campaignRecipientRepository;
    }

    public Lead createLead(LeadRequestDTO requestDTO) {
        Lead lead = new Lead();
        lead.setName(requestDTO.name());
        lead.setEmail(requestDTO.email());
        lead.setSubscribed(
                requestDTO.subscribed() != null ? requestDTO.subscribed() : true // Valor predeterminado
        );

        lead = leadRepository.save(lead);

        Campaign leadCampaign = campaignRepository.findByName("Leads Campaign")
                .orElseThrow(() -> new RuntimeException("La campaña no existe"));
        // 🟢 **Asignar a la campaña correspondiente**
        if (leadCampaign != null) { // 🔥 Si la campaña está definida, la asigna
            CampaignRecipient campaignRecipient = new CampaignRecipient();
            campaignRecipient.setCampaign(leadCampaign);
            campaignRecipient.setRecipient(lead);
            campaignRecipient.setEmailStep(0);
            campaignRecipient.setNextEmailDate(LocalDateTime.now()); // Enviar el primer email de inmediato
            campaignRecipient.setStatus(CampaignRecipient.Status.PENDING);

            campaignRecipientRepository.save(campaignRecipient);
        }

        return lead;
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

    @Transactional
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
