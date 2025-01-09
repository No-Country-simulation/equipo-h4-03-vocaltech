package com.vocaltech.api.controller.auth;

import com.vocaltech.api.dto.request.auth.LoginRequestDto;
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

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login (@Valid @RequestBody LoginRequestDto dto) {
        return ResponseEntity.ok().body(authService.login(dto));
    }
}
