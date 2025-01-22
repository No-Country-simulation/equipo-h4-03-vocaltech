package com.vocaltech.api.controller;

import com.vocaltech.api.domain.leads.Lead;
import com.vocaltech.api.domain.leads.LeadRequestDTO;
import com.vocaltech.api.domain.leads.LeadResponseDTO;
import com.vocaltech.api.domain.leads.LeadService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/leads")
public class LeadController {

    private final LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    // Crear un lead
    @PostMapping
    public ResponseEntity<LeadResponseDTO> createLead(@Valid @RequestBody LeadRequestDTO requestDTO) {
        Lead lead = leadService.createLead(requestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(lead.getLeadId())
                .toUri();

        return ResponseEntity.created(location).body(LeadResponseDTO.fromEntity(lead));
    }

    // Obtener todos los leads
    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<List<LeadResponseDTO>> getAllLeads() {
        List<LeadResponseDTO> leads = leadService.getAllLeads()
                .stream()
                .map(LeadResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(leads);
    }

    // Obtener los leads subscriptos
    @GetMapping("/subscribed")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<List<LeadResponseDTO>> getSubscribedLeads() {
        List<LeadResponseDTO> leads = leadService.getSubscribedLeads()
                .stream()
                .map(LeadResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(leads);
    }

    @PatchMapping("/{id}/unsubscribe")
    public ResponseEntity<String> unsubscribeLead(@PathVariable UUID id) {
        leadService.unsubscribe(id);
        return ResponseEntity.ok("You have been unsubscribed successfully.");
    }

    // Obtener un lead por ID
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<LeadResponseDTO> getLeadById(@PathVariable UUID id) {
        Lead lead = leadService.getLeadById(id);
        return ResponseEntity.ok(LeadResponseDTO.fromEntity(lead));
    }

    // Eliminar un lead
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Void> deleteLead(@PathVariable UUID id) {
        leadService.deleteLead(id);
        return ResponseEntity.noContent().build();
    }
}
