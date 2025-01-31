package com.vocaltech.api.domain.leads;

import java.time.LocalDateTime;
import java.util.UUID;

public record LeadResponseDTO(

        String name,
        String email,
        LocalDateTime creationDate
) {
    public static LeadResponseDTO fromEntity(Lead lead) {
        return new LeadResponseDTO(

                lead.getName(),
                lead.getEmail(),
                lead.getCreatedAt()
        );
    }
}
