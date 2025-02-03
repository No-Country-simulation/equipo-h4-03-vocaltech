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
    @Scheduled(fixedRate = 60000)
    public void processScheduledEmails() {
        List<CampaignRecipient> pendingRecipients = campaignRecipientRepository.findPendingEmails();

        System.out.println("🟢 Emails pendientes encontrados: " + pendingRecipients.size());

        for (CampaignRecipient recipient : pendingRecipients) {
            Optional<CampaignEmail> campaignEmail = campaignEmailRepository.findByCampaignAndStep(
                    recipient.getCampaign().getCampaignId(), recipient.getEmailStep()
            );

            if (campaignEmail.isEmpty()) {
                System.out.println("⚠️ No se encontró un email para la campaña ID: " + recipient.getCampaign().getCampaignId() + " y el paso " + recipient.getEmailStep());
            }

            campaignEmail.ifPresent(email -> sendEmail(recipient, email));
        }
    }



    private void sendEmail(CampaignRecipient recipient, CampaignEmail campaignEmail) {
        String templateKey = campaignEmail.getTemplateKey();
        String emailBody = templateService.processTemplate(templateKey, recipient.getRecipient().getRecipientId()); // Procesa el template con Thymeleaf
        List<File> attachments = fetchAttachmentsIfNeeded(recipient, campaignEmail);

        mailService.sendEmailWithFiles(
                recipient.getRecipient().getEmail(),
                campaignEmail.getSubject(),
                emailBody,
                attachments
        );

        System.out.println("✅ Email enviado a: " + recipient.getRecipient().getEmail());

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
            int delayDays = nextEmail.get().getDelayDays();
            recipient.setNextEmailDate(startDate.plusDays(delayDays));

        }

        campaignRecipientRepository.save(recipient);
    }

}
