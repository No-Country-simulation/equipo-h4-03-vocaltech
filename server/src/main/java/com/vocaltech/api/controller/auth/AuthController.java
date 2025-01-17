package com.vocaltech.api.controller.auth;

import com.vocaltech.api.config.CurrentUser;
import com.vocaltech.api.dto.request.auth.LoginRequestDto;
import com.vocaltech.api.dto.request.auth.RefreshTokenRequest;
import com.vocaltech.api.dto.request.auth.RegisterRequestDto;
import com.vocaltech.api.dto.response.auth.AuthResponseDto;
import com.vocaltech.api.model.User;
import com.vocaltech.api.service.interfaces.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
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

    @GetMapping("/check-login")
    public ResponseEntity<AuthResponseDto> checkLogin (@CurrentUser User user) {
        return ResponseEntity.ok().body(authService.checkLogin(user.getEmail()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDto> refreshToken(@RequestBody @Valid RefreshTokenRequest request)  {
        return ResponseEntity.ok(authService.refreshToken(request.refreshToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String accessToken, @RequestBody @Valid RefreshTokenRequest request) {
        authService.logout(request.refreshToken(), accessToken.substring(7));
        return ResponseEntity.noContent().build();
    }

}
