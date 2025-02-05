package com.vocaltech.api.domain.recipients;

import com.vocaltech.api.domain.entrepreneurs.Entrepreneur;
import lombok.Builder;


import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record RecipientResponseDTO(
        UUID recipientId,
        String name,
        String email,
        String phone,
        Boolean MVP,
        Boolean hireJunior,
        Boolean moreInfo,
        Boolean subscribed,
        LocalDateTime createdAt,
        String recipientType  // Para que el frontend pueda diferenciar los tipos

) {

       public static RecipientResponseDTO fromEntity(Recipient recipient) {
        return new RecipientResponseDTO(
                recipient.getRecipientId(),
                recipient.getName(),
                recipient.getEmail(),
                recipient.getPhone(),
                recipient.getMVP(),
                recipient.getHireJunior(),
                recipient.getMoreInfo(),
                recipient.getSubscribed(),
                recipient.getCreatedAt(),
                recipient.getRecipientType()

        );
    }
}
