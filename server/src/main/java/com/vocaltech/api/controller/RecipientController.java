package com.vocaltech.api.controller;


import com.vocaltech.api.domain.companies.Company;
import com.vocaltech.api.domain.entrepreneurs.Entrepreneur;
import com.vocaltech.api.domain.leads.Lead;
import com.vocaltech.api.domain.recipients.Recipient;
import com.vocaltech.api.domain.recipients.RecipientResponseDTO;
import com.vocaltech.api.domain.recipients.RecipientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recipients")
@RequiredArgsConstructor
public class RecipientController {

    private final RecipientService recipientService;

    @GetMapping
    public ResponseEntity<List<RecipientResponseDTO>> getAllRecipients() {
        return ResponseEntity.ok(recipientService.getAllRecipients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipientById(@PathVariable UUID id) {
        Object recipientDTO = recipientService.getRecipientById(id);
        return recipientDTO != null ? ResponseEntity.ok(recipientDTO) : ResponseEntity.notFound().build();
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<RecipientResponseDTO>> getRecipientsByType(@PathVariable String type) {
        Class<?> clazz;
        switch (type.toLowerCase()) {
            case "lead":
                clazz = Lead.class;
                break;
            case "entrepreneur":
                clazz = Entrepreneur.class;
                break;
            case "company":
                clazz = Company.class;
                break;
            default:
                return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(recipientService.getRecipientsByType((Class<? extends Recipient>) clazz));
    }

    @GetMapping("/campaign/{campaignId}")
    public ResponseEntity<List<RecipientResponseDTO>> getRecipientsByCampaign(@PathVariable UUID campaignId) {
        return ResponseEntity.ok(recipientService.getRecipientsByCampaign(campaignId));
    }
}
