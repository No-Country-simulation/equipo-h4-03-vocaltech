package com.vocaltech.api.service.interfaces;

import com.vocaltech.api.dto.request.auth.LoginRequestDto;
import com.vocaltech.api.dto.request.auth.RegisterRequestDto;
import com.vocaltech.api.dto.response.auth.AuthResponseDto;

public interface AuthService {
    AuthResponseDto register(RegisterRequestDto dto);
    AuthResponseDto login(LoginRequestDto dto);
}
