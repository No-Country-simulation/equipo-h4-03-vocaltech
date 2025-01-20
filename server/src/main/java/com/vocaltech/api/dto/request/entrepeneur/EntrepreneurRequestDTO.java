package com.vocaltech.api.dto.request.entrepeneur;

import com.vocaltech.api.model.Entrepreneur;
import jakarta.validation.constraints.*;

public record EntrepreneurRequestDTO(

        @NotBlank(message = "El nombre es obligatorio")
        String name,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "Debe ser un email válido")
        String email,

        @Pattern(regexp = "\\+?[0-9]{7,15}", message = "El teléfono debe ser un número válido")
        String phone,

        @NotNull(message = "El tipo de emprendimiento es obligatorio")
        Entrepreneur.EntrepreneurType type,

        String description,

        @NotNull(message = "Debe indicar si desea desarrollar un MVP")
        Boolean MVP,

        Entrepreneur.ProductToDevelop productToDevelop,

        @NotNull(message = "Debe indicar si busca contratar talento junior")
        Boolean hireJunior,

        @NotNull(message = "Debe indicar si desea más información")
        Boolean moreInfo,

        Boolean active
) {

    // Si quieres manejar el valor por defecto en el DTO o en el constructor, podrías hacerlo aquí
    public EntrepreneurRequestDTO {
        if (active == null) {
            active = true; // Establecer como 'true' por defecto
        }
    }

    public static EntrepreneurRequestDTO from(EntrepreneurRequestDTO dto) {
        // Puedes realizar modificaciones o simplemente devolver una copia
        return new EntrepreneurRequestDTO(
                dto.name(),
                dto.email(),
                dto.phone(),
                dto.type(),
                dto.description(),
                dto.MVP(),
                dto.productToDevelop(),
                dto.hireJunior(),
                dto.moreInfo(),
                dto.active()
        );
    }
}
