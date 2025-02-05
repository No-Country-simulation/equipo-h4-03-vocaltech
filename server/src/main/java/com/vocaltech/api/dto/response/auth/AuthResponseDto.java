package com.vocaltech.api.dto.response.auth;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"user", "token", "refreshToken"})
public record AuthResponseDto(
        UserResponseDto user,
        String token,
        String refreshToken
) {
}
