package com.vocaltech.api.service.implementation;

import com.vocaltech.api.config.mapper.UserMapper;
import com.vocaltech.api.config.security.JwtService;
import com.vocaltech.api.dto.request.auth.LoginRequestDto;
import com.vocaltech.api.dto.request.auth.RegisterRequestDto;
import com.vocaltech.api.dto.response.auth.AuthResponseDto;
import com.vocaltech.api.dto.response.auth.UserResponseDto;
import com.vocaltech.api.exception.BadRequestException;
import com.vocaltech.api.exception.InvalidTokenException;
import com.vocaltech.api.exception.NotFoundException;
import com.vocaltech.api.exception.TokenExpiredException;
import com.vocaltech.api.model.Role;
import com.vocaltech.api.model.TokenBlacklist;
import com.vocaltech.api.model.User;
import com.vocaltech.api.repository.IRoleRepository;
import com.vocaltech.api.repository.IUserRepository;
import com.vocaltech.api.repository.TokenBlacklistRepository;
import com.vocaltech.api.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper mapper;
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenBlacklistRepository tokenBlacklistRepository;

    @Transactional
    @Override
    public AuthResponseDto register(RegisterRequestDto dto) {
        Optional<User> userFound = userRepository.findUserByEmail(dto.getEmail());
        if(userFound.isPresent()) throw new BadRequestException(String.format("Email is already registered: %s",dto.getEmail()));

        String roleName = dto.getUserType() ? "ROLE_ENTREPRENEUR" : "ROLE_COMPANY_LEADER";
        Role role = roleRepository.findRoleByName(roleName).orElseThrow(() -> new NotFoundException(String.format("Role not found with name %s",roleName)));

        User newUser = new User();
        newUser.setEmail(dto.getEmail());
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        newUser.getRoles().add(role);

        userRepository.save(newUser);
        return generateResponse(newUser);
    }

    @Override
    public AuthResponseDto login(LoginRequestDto dto) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        } catch (Exception e) {
            throw new BadRequestException("Invalid username or password");
        }
        User user = userRepository.findUserByEmail(dto.getEmail())
                .orElseThrow(() -> new NotFoundException(String.format("User not found with email: %s",dto.getEmail())));

        return generateResponse(user);
    }

    @Override
    public AuthResponseDto checkLogin(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format("User not found with email: %s",email)));
        return generateResponse(user);
    }

    @Transactional
    @Override
    public void logout(String refreshToken, String accessToken) {
        String username = jwtService.getUsernameFromToken(refreshToken);

        User user = userRepository.findUserByEmail(username)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con el correo: " + username));

        user.setRefreshToken(null);
        userRepository.save(user);

        TokenBlacklist tokenBlacklist = new TokenBlacklist();
        tokenBlacklist.setToken(accessToken);
        tokenBlacklist.setExpiryDate(jwtService.getExpiration(accessToken));
        tokenBlacklistRepository.save(tokenBlacklist);
    }

    @Transactional
    @Override
    public AuthResponseDto refreshToken(String refreshToken) {
        if (!jwtService.isTokenExpired(refreshToken)) {
            throw new TokenExpiredException("El token de refresco ha expirado.", null);
        }

        String username = jwtService.getUsernameFromToken(refreshToken);

        User user = userRepository.findUserByEmail(username)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con el correo: " + username));

        if (!refreshToken.equals(user.getRefreshToken())) {
            throw new InvalidTokenException("El token de refresco no coincide.", null);
        }

        String newToken = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        user.setRefreshToken(newRefreshToken);
        userRepository.save(user);

        return new AuthResponseDto(mapper.toUserResponseDTO(user), newToken, newRefreshToken);
    }

    @Transactional
    private AuthResponseDto generateResponse(User user) {
        UserResponseDto userR = mapper.toUserResponseDTO(user);

        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return new AuthResponseDto(userR,token, refreshToken);
    }
}
