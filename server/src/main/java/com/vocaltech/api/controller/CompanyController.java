package com.vocaltech.api.controller;

import com.vocaltech.api.domain.companies.Company;
import com.vocaltech.api.domain.companies.CompanyRequestDTO;
import com.vocaltech.api.domain.companies.CompanyResponseDTO;
import com.vocaltech.api.domain.companies.CompanyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/companies")
@SecurityRequirement(name = "bearer-key")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // Crear una empresa
    @PostMapping
    public ResponseEntity<CompanyResponseDTO> createCompany(@Valid @RequestBody CompanyRequestDTO requestDTO) {
        Company company = companyService.createCompany(requestDTO);

        // Crear la URL del recurso creado
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(company.getCompanyId())
                .toUri();

        // Devolver 201 Created con la ubicación del recurso
        return ResponseEntity.created(location).body(CompanyResponseDTO.fromEntity(company));

    }

    // Obtener todas las empresas
    @GetMapping
    public ResponseEntity<List<CompanyResponseDTO>> getAllCompanies() {
        try {
            List<CompanyResponseDTO> companies = companyService.getActiveCompanies()
                    .stream()
                    .map(CompanyResponseDTO::fromEntity)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(companies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener un emprendedor por ID
    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponseDTO> getCompanyById(@PathVariable UUID id) {
        Company company = companyService.getCompanyById(id);
        return ResponseEntity.ok(CompanyResponseDTO.fromEntity(company));
    }

    // Actualizar un emprendedor
    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponseDTO> updateCompany(
            @PathVariable UUID id,
            @Valid @RequestBody CompanyRequestDTO requestDTO) {
        Company updated = companyService.updateCompany(id, requestDTO);
        return ResponseEntity.ok(CompanyResponseDTO.fromEntity(updated));
    }

    // Eliminar un emprendedor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntrepreneur(@PathVariable UUID id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}
