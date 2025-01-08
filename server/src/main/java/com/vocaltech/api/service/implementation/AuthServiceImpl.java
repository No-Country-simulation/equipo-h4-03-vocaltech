package com.vocaltech.api.service.implementation;

import com.vocaltech.api.config.mapper.UserMapper;
import com.vocaltech.api.dto.request.auth.RegisterRequestDto;
import com.vocaltech.api.dto.response.auth.AuthResponseDto;
import com.vocaltech.api.dto.response.auth.UserResponseDto;
import com.vocaltech.api.exception.BadRequestException;
import com.vocaltech.api.exception.NotFoundException;
import com.vocaltech.api.model.Role;
import com.vocaltech.api.model.User;
import com.vocaltech.api.repository.IRoleRepository;
import com.vocaltech.api.repository.IUserRepository;
import com.vocaltech.api.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper mapper;
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

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

    private AuthResponseDto generateResponse(User user) {
        UserResponseDto userR = mapper.toUserResponseDTO(user);
        return null;
    }
}
