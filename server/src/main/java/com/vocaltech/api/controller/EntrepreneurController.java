package com.vocaltech.api.controller;

import com.google.zxing.WriterException;
import com.vocaltech.api.domain.entrepreneurs.EntrepreneurRequestDTO;
import com.vocaltech.api.domain.entrepreneurs.EntrepreneurResponseDTO;
import com.vocaltech.api.domain.entrepreneurs.Entrepreneur;
import com.vocaltech.api.domain.entrepreneurs.EntrepreneurService;
import com.vocaltech.api.service.TranscriptionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
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
@RequestMapping("/api/entrepreneurs")
public class EntrepreneurController {

    private final EntrepreneurService entrepreneurService;
    private final TranscriptionService transcriptionService;

    public EntrepreneurController(EntrepreneurService entrepreneurService, TranscriptionService transcriptionService) {
        this.entrepreneurService = entrepreneurService;
        this.transcriptionService = transcriptionService;
    }

    // Crear un emprendedor
    @PostMapping()
    public ResponseEntity<EntrepreneurResponseDTO> createEntrepreneur(
            @ModelAttribute EntrepreneurRequestDTO requestDTO,
            @RequestParam(required = false) UUID leadId
    ) {
        try {

            Resource audioResource = requestDTO.audioFile().getResource();

            InputStream audioInputStream = requestDTO.audioFile().getInputStream();

            Entrepreneur entrepreneur = entrepreneurService.createEntrepreneur(
                    requestDTO,
                    leadId,
                    audioInputStream,
                    audioResource,
                    requestDTO.audioFile().getOriginalFilename()
            );

            // Construir la URI del recurso creado
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(entrepreneur.getEntrepreneurId())
                    .toUri();

            // Retornar la respuesta con 201 Created y el DTO del recurso creado
            return ResponseEntity.created(location).body(EntrepreneurResponseDTO.fromEntity(entrepreneur));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
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
