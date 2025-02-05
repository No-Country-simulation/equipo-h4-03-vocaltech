package com.vocaltech.api.domain.leads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ILeadRepository extends JpaRepository<Lead, UUID> {
    List<Lead> findBySubscribedTrue();

    Optional<Lead> findByEmail(@NotBlank(message = "El email es obligatorio") @Email(message = "Debe ser un email válido") String email);
}