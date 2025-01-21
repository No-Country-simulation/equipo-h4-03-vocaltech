package com.vocaltech.api.domain.companies;

import com.vocaltech.api.domain.entrepreneurs.Entrepreneur;
import com.vocaltech.api.domain.entrepreneurs.EntrepreneurRequestDTO;
import jakarta.validation.constraints.*;

import java.util.List;

public record CompanyRequestDTO(
        @NotBlank(message = "El nombre de la empresa es obligatorio")
        String companyName,

        String sector,

        @NotNull(message = "El tamaño de la empresa es obligatorio")
        Company.CompanySize size,

        @NotBlank(message = "El nombre del contacto es obligatorio")
        String contactName,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "Debe ser un email válido")
        String email,

        @Pattern(regexp = "\\+?[0-9]{7,15}", message = "El teléfono debe ser un número válido")
        String phone,

        String description,

        @NotNull(message = "Debe indicar si desea desarrollar un MVP")
        Boolean MVP,

        Company.DevelopmentStage developmentStage,

        @NotNull(message = "Debe indicar si busca contratar talento junior")
        Boolean hireJunior,

        List<String> talentProfile,

        @NotNull(message = "Debe indicar si desea más información")
        Boolean moreInfo,

        @NotEmpty(message = "Debe seleccionar al menos un servicio")
        List<String> products,

        Boolean active
) {
    // Si quieres manejar el valor por defecto en el DTO o en el constructor, podrías hacerlo aquí
    public CompanyRequestDTO {
        if (active == null) {
            active = true; // Establecer como 'true' por defecto
        }
    }

    public static CompanyRequestDTO from(CompanyRequestDTO dto) {
        // Puedes realizar modificaciones o simplemente devolver una copia
        return new CompanyRequestDTO(
                dto.companyName(),
                dto.sector(),
                dto.size(),
                dto.contactName(),
                dto.email(),
                dto.phone(),
                dto.description(),
                dto.MVP(),
                dto.developmentStage(),
                dto.hireJunior(),
                dto.talentProfile(),
                dto.moreInfo(),
                dto.products(),
                dto.active()
        );
    }
}
