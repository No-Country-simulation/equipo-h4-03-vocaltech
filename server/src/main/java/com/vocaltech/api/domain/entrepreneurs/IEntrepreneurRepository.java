package com.vocaltech.api.domain.entrepreneurs;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IEntrepreneurRepository extends JpaRepository<Entrepreneur, UUID> {
    List<Entrepreneur> findByActiveTrue();
    boolean existsByLeadLeadId(UUID leadId);
}