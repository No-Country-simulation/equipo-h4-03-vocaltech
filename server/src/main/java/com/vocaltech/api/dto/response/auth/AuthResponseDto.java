package com.vocaltech.api.dto.response.auth;

public record AuthResponseDto(
        UserResponseDto user,
        String token,
        String refreshToken
) {
}
