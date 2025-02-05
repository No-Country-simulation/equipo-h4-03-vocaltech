package com.vocaltech.api.domain.leads;

import com.vocaltech.api.domain.entrepreneurs.EntrepreneurRequestDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LeadRequestDTO(
        @NotBlank(message = "Name is mandatory")
        String name,

        @NotBlank(message = "Email is mandatory")
        @Email(message = "Invalid email format")
        String email,
        Boolean subscribed
) {
    public LeadRequestDTO {
        if (subscribed == null) {
            subscribed = true; // Establecer como 'true' por defecto
        }
    }

    public static LeadRequestDTO from(LeadRequestDTO dto) {
        // Puedes realizar modificaciones o simplemente devolver una copia
        return new LeadRequestDTO(
                dto.name(),
                dto.email(),
                dto.subscribed()
        );
    }

}
