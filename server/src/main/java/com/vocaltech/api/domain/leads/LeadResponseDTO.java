package com.vocaltech.api.domain.leads;

import java.time.LocalDateTime;
import java.util.UUID;

public record LeadResponseDTO(
        UUID leadId,
        String name,
        String email,
        LocalDateTime creationDate
) {
    public static LeadResponseDTO fromEntity(Lead lead) {
        return new LeadResponseDTO(
                lead.getLeadId(),
                lead.getName(),
                lead.getEmail(),
                lead.getCreationDate()
        );
    }
}
