package com.vocaltech.api.service;


import com.vocaltech.api.domain.recipients.IRecipientRepository;
import com.vocaltech.api.domain.recipients.Recipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.UUID;

@Service
public class TemplateService {

    private final S3Service s3Service;
    private final TemplateEngine templateEngine;
    private final IRecipientRepository recipientRepository;

    @Autowired
    public TemplateService(S3Service s3Service, TemplateEngine templateEngine, IRecipientRepository recipientRepository) {
        this.s3Service = s3Service;
        this.templateEngine = templateEngine;
        this.recipientRepository = recipientRepository;
    }

    @Cacheable(value = "templates", key = "#templateKey")
    private String getTemplate(String templateKey) {
        return s3Service.downloadTemplateAsString(templateKey);
    }

    public String processTemplate(String templateKey, UUID recipientId) {

        Recipient recipient = recipientRepository.findById(recipientId)
                .orElseThrow(() -> new IllegalArgumentException("Recipient no encontrado"));


        Context context = new Context();
        context.setVariable("leadName",  recipient.getName()); // Nombre del lead
        context.setVariable("leadId", recipient.getRecipientId().toString()); // ID del lead

        return templateEngine.process(templateKey, context); // Procesa el template
    }

}
