package com.vocaltech.api.domain.recipients;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IRecipientRepository extends JpaRepository<Recipient, UUID> {

}
