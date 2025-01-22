package com.vocaltech.api.domain.leads;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ILeadRepository extends JpaRepository<Lead, UUID> {
    List<Lead> findBySubscribedTrue();
}