package com.vocaltech.api.scheduler;

import com.vocaltech.api.domain.campaigns.CampaignEmail;
import com.vocaltech.api.domain.campaigns.CampaignRecipient;
import com.vocaltech.api.domain.campaigns.ICampaignEmailRepository;
import com.vocaltech.api.domain.campaigns.ICampaignRecipientRepository;
import com.vocaltech.api.service.MailServices;
import com.vocaltech.api.service.S3Service;
import com.vocaltech.api.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class EmailScheduler {
    private final ICampaignRecipientRepository campaignRecipientRepository;
    private final ICampaignEmailRepository campaignEmailRepository;
    private final MailServices mailService;
    private final S3Service s3Service;
    private final TemplateService templateService;

    @Autowired
    public EmailScheduler(ICampaignRecipientRepository campaignRecipientRepository,
                          ICampaignEmailRepository campaignEmailRepository,
                          MailServices mailService,
                          S3Service s3Service, TemplateService templateService) {
        this.campaignRecipientRepository = campaignRecipientRepository;
        this.campaignEmailRepository = campaignEmailRepository;
        this.mailService = mailService;
        this.s3Service = s3Service;
        this.templateService = templateService;
    }

    // Se ejecuta cada 30 minutos
    @Scheduled(fixedRate = 1800000)
    public void processScheduledEmails() {
        List<CampaignRecipient> pendingRecipients = campaignRecipientRepository.findPendingEmails();

        for (CampaignRecipient recipient : pendingRecipients) {
            Optional<CampaignEmail> campaignEmail = campaignEmailRepository.findByCampaignAndStep(
                    recipient.getCampaign().getCampaignId(), recipient.getEmailStep()
            );

            campaignEmail.ifPresent(email -> sendEmail(recipient, email));
        }
    }

    private String downloadTemplateFromS3(String templateKey) {
        return templateService.getTemplate(templateKey); // Usa el servicio de caching
    }


    private void sendEmail(CampaignRecipient recipient, CampaignEmail campaignEmail) {
        String templateContent = downloadTemplateFromS3(campaignEmail.getTemplateKey()); // Descarga el template como String
        String emailBody = templateService.processTemplate(templateContent, recipient.getRecipient().getRecipientId()); // Procesa el template con Thymeleaf
        List<File> attachments = fetchAttachmentsIfNeeded(recipient, campaignEmail);

        mailService.sendEmailWithFiles(
                recipient.getRecipient().getEmail(),
                campaignEmail.getSubject(),
                emailBody,
                attachments
        );

        updateRecipientForNextEmail(recipient, campaignEmail);
    }

    private String loadTemplateFromS3(String templateKey) {
        return s3Service.generatePresignedUrl(templateKey, Duration.ofHours(1));
    }

    private List<File> fetchAttachmentsIfNeeded(CampaignRecipient recipient, CampaignEmail campaignEmail) {
        if (campaignEmail.isHasAttachments()) {
            // Aquí puedes buscar los archivos en función del recipient
        }
        return List.of();
    }

    private void updateRecipientForNextEmail(CampaignRecipient recipient, CampaignEmail campaignEmail) {
        recipient.setEmailStep(recipient.getEmailStep() + 1);

        Optional<CampaignEmail> nextEmail = campaignEmailRepository.findByCampaignAndStep(
                recipient.getCampaign().getCampaignId(), recipient.getEmailStep()
        );

        if (nextEmail.isPresent()) {

            LocalDateTime startDate = recipient.getRecipient().getCreatedAt();
            double delayDays = nextEmail.get().getDelayDays();
            recipient.setNextEmailDate(startDate.plusDays((long) delayDays));

        }

        campaignRecipientRepository.save(recipient);
    }

}
