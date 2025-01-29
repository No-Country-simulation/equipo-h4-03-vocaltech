package com.vocaltech.api.domain.companies;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ICompanyRepository extends JpaRepository<Company, UUID> {
    List<Company> findBySubscribedTrue();

}
