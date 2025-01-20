package com.vocaltech.api.repository;

import com.vocaltech.api.model.Entrepreneur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EntrepreneurRepository extends JpaRepository<Entrepreneur, UUID> {
    List<Entrepreneur> findByActiveTrue();
}