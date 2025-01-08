package com.vocaltech.api.dto.response.auth;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Set;
import java.util.UUID;

@JsonPropertyOrder({"userId", "email", "name", "lastname", "dni", "roles"})
public record UserResponseDto(
        UUID userId,
        String email,
        String name,
        String lastname,
        String dni,
        Set<RoleResponseDto> roles
) {
}
