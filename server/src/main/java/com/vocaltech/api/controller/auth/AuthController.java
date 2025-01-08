package com.vocaltech.api.controller.auth;

import com.vocaltech.api.dto.request.auth.RegisterRequestDto;
import com.vocaltech.api.dto.response.auth.AuthResponseDto;
import com.vocaltech.api.service.interfaces.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterRequestDto dto) {
        AuthResponseDto response = authService.register(dto);
        return ResponseEntity.status(201).body(response);
    }
}
