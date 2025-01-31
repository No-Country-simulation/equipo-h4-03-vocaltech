package com.vocaltech.api.domain.recipients;

import com.vocaltech.api.domain.companies.Company;
import com.vocaltech.api.domain.companies.CompanyResponseDTO;
import com.vocaltech.api.domain.entrepreneurs.Entrepreneur;
import com.vocaltech.api.domain.entrepreneurs.EntrepreneurResponseDTO;
import com.vocaltech.api.domain.leads.Lead;
import com.vocaltech.api.domain.leads.LeadResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipientService {

    private final IRecipientRepository recipientRepository;

    public List<RecipientResponseDTO> getAllRecipients() {
        return recipientRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

//    public RecipientResponseDTO getRecipientById(UUID id) {
//        return recipientRepository
//                .findById(id)
//                .map(this::convertToDTO)
//                .orElse(null);
//    }

    public Object getRecipientById(UUID id) {
        return recipientRepository
                .findById(id)
                .map(this::convertToDTOById)
                .orElse(null);
    }

    public List<RecipientResponseDTO> getRecipientsByType(Class<? extends Recipient> type) {
        return recipientRepository
                .findByRecipientType(type)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<RecipientResponseDTO> getRecipientsByCampaign(UUID campaignId) {
        return recipientRepository
                .findByCampaignId(campaignId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private RecipientResponseDTO convertToDTO(Recipient recipient) {
        return RecipientResponseDTO.builder()
                .recipientId(recipient.getRecipientId())
                .name(recipient.getName())
                .email(recipient.getEmail())
                .phone(recipient.getPhone())
                .MVP(recipient.getMVP())
                .hireJunior(recipient.getHireJunior())
                .moreInfo(recipient.getMoreInfo())
                .subscribed(recipient.getSubscribed())
                .createdAt(recipient.getCreatedAt())
                .recipientType(recipient.getClass().getSimpleName())
                .build();
    }



    private Object convertToDTOById(Recipient recipient) {
        if (recipient instanceof Lead lead) {
            return LeadResponseDTO.fromEntity(lead);
        } else if (recipient instanceof Entrepreneur entrepreneur) {
            return EntrepreneurResponseDTO.fromEntity(entrepreneur);
        } else if (recipient instanceof Company company) {
            return CompanyResponseDTO.fromEntity(company);
        } else {
            return RecipientResponseDTO.fromEntity(recipient);
        }
    }

}

