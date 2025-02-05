package com.vocaltech.api.controller;

import com.vocaltech.api.domain.companies.Company;
import com.vocaltech.api.domain.companies.CompanyRequestDTO;
import com.vocaltech.api.domain.companies.CompanyResponseDTO;
import com.vocaltech.api.domain.companies.CompanyService;
import com.vocaltech.api.domain.entrepreneurs.Entrepreneur;
import com.vocaltech.api.domain.entrepreneurs.EntrepreneurRequestDTO;
import com.vocaltech.api.domain.entrepreneurs.EntrepreneurResponseDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // Crear una empresa
    @PostMapping()
    public ResponseEntity<CompanyResponseDTO> createCompany(
            @ModelAttribute CompanyRequestDTO requestDTO
    ) {
        try {

            InputStream audioInputStream = requestDTO.audioFile().getInputStream();

            Company company = companyService.createCompany(
                    requestDTO,
                    audioInputStream,
                    requestDTO.audioFile().getOriginalFilename()
            );

            // Construir la URI del recurso creado
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(company.getRecipientId())
                    .toUri();

            // Retornar la respuesta con 201 Created y el DTO del recurso creado
            return ResponseEntity.created(location).body(CompanyResponseDTO.fromEntity(company));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Obtener todas las empresas
    @GetMapping
    @SecurityRequirement(name = "bearer-key")
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
    @SecurityRequirement(name = "bearer-key")
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

    @PatchMapping("/{id}/unsubscribe")
    public ResponseEntity<String> unsubscribeLead(@PathVariable UUID id) {
        companyService.unsubscribe(id);
        return ResponseEntity.ok("You have been unsubscribed successfully.");
    }

    // Eliminar un emprendedor
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Void> deleteEntrepreneur(@PathVariable UUID id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}
