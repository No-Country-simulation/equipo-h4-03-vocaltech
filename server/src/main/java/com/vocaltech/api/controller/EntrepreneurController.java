package com.vocaltech.api.controller;

import com.vocaltech.api.domain.entrepreneurs.EntrepreneurRequestDTO;
import com.vocaltech.api.domain.entrepreneurs.EntrepreneurResponseDTO;
import com.vocaltech.api.domain.entrepreneurs.Entrepreneur;
import com.vocaltech.api.domain.entrepreneurs.EntrepreneurService;
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
@RequestMapping("/api/entrepreneurs")
public class EntrepreneurController {

    private final EntrepreneurService entrepreneurService;

    public EntrepreneurController(EntrepreneurService entrepreneurService) {
        this.entrepreneurService = entrepreneurService;
    }

    // Crear un emprendedor
    @PostMapping
    public ResponseEntity<EntrepreneurResponseDTO> createEntrepreneur(@Valid @RequestBody EntrepreneurRequestDTO requestDTO) {
        Entrepreneur entrepreneur = entrepreneurService.createEntrepreneur(requestDTO);

        // Crear la URL del recurso creado
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entrepreneur.getEntrepreneurId())
                .toUri();

        // Devolver 201 Created con la ubicación del recurso
        return ResponseEntity.created(location).body(EntrepreneurResponseDTO.fromEntity(entrepreneur));

    }

    // Obtener todos los emprendedores
    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<List<EntrepreneurResponseDTO>> getAllEntrepreneurs() {
        try {
            List<EntrepreneurResponseDTO> entrepreneurs = entrepreneurService.getActiveEntrepreneurs()
                    .stream()
                    .map(EntrepreneurResponseDTO::fromEntity)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(entrepreneurs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener un emprendedor por ID
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<EntrepreneurResponseDTO> getEntrepreneurById(@PathVariable UUID id) {
        Entrepreneur entrepreneur = entrepreneurService.getEntrepreneurById(id);
        return ResponseEntity.ok(EntrepreneurResponseDTO.fromEntity(entrepreneur));
    }

    // Actualizar un emprendedor
    @PutMapping("/{id}")
    public ResponseEntity<EntrepreneurResponseDTO> updateEntrepreneur(
            @PathVariable UUID id,
            @Valid @RequestBody EntrepreneurRequestDTO requestDTO) {
        Entrepreneur updated = entrepreneurService.updateEntrepreneur(id, requestDTO);
        return ResponseEntity.ok(EntrepreneurResponseDTO.fromEntity(updated));
    }

    @PatchMapping("/{id}/unsubscribe")
    public ResponseEntity<String> unsubscribeLead(@PathVariable UUID id) {
        entrepreneurService.unsubscribe(id);
        return ResponseEntity.ok("You have been unsubscribed successfully.");
    }

    // Eliminar un emprendedor
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Void> deleteEntrepreneur(@PathVariable UUID id) {
        entrepreneurService.deleteEntrepreneur(id);
        return ResponseEntity.noContent().build();
    }
}
