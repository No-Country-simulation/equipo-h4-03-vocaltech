package com.vocaltech.api.domain.entrepreneurs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IEntrepreneurRepository extends JpaRepository<Entrepreneur, UUID> {
    List<Entrepreneur> findBySubscribedTrue();

    Optional<Entrepreneur> findByEmail(@NotBlank(message = "El email es obligatorio") @Email(message = "Debe ser un email válido") String email);
}