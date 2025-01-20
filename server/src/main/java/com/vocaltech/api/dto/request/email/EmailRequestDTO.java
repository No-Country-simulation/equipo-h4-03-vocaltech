package com.vocaltech.api.dto.request.email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record EmailRequestDTO (
        @NotEmpty(message = "Recipient email is mandatory")
        List<@Email(message = "Invalid email format") String> toUser,

        @NotBlank(message = "Subject is mandatory")
        String subject,

        @NotBlank(message = "Body is mandatory")
        String message
) {
}
