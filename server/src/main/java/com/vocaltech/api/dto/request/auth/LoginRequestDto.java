package com.vocaltech.api.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    @Email(message = "Correo electrónico no válido ingresado")
    @NotBlank(message = "El correo electrónico no puede estar vacío")
    private String email;
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,12}$",
            message = "La contraseña debe tener entre 8 y 12 caracteres, incluyendo una letra mayúscula, una letra minúscula, un número y un carácter especial (ejemplo: '@', '$', '!', '%')."
    )
    private String password;
}
