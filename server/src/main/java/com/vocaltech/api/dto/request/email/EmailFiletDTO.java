package com.vocaltech.api.dto.request.email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record EmailFiletDTO(
        @NotEmpty
        List<@Email String> toUser,

        @NotBlank(message = "Subject is mandatory")
        String subject,

        @NotBlank(message = "Body is mandatory")
        String message,

        @NotNull MultipartFile file
) {
}
