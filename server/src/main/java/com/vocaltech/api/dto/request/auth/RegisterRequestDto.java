package com.vocaltech.api.dto.request.auth;

import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {
    @Email(message = "El correo electrónico ingresado no es válido")
    @NotBlank(message = "El correo electrónico es obligatorio")
    private String email;

    @Email(message = "Correo electrónico no válido ingresado")
    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,12}$",
            message = "La contraseña debe tener entre 8 y 12 caracteres, incluyendo una letra mayúscula, una letra minúscula, un número y un carácter especial (ejemplo: '@', '$', '!', '%')."
    )
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;
    private Boolean userType = false;
}
