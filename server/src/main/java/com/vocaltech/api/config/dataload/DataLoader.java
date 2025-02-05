package com.vocaltech.api.config.dataload;

import com.vocaltech.api.domain.campaigns.Campaign;
import com.vocaltech.api.domain.campaigns.CampaignEmail;
import com.vocaltech.api.domain.campaigns.ICampaignEmailRepository;
import com.vocaltech.api.domain.campaigns.ICampaignRepository;
import com.vocaltech.api.service.S3Service;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class DataLoader implements CommandLineRunner {
    private final ICampaignRepository campaignRepository;
    private final ICampaignEmailRepository campaignEmailRepository;
    private final S3Service s3Service;

    public DataLoader(ICampaignRepository campaignRepository, ICampaignEmailRepository campaignEmailRepository, S3Service s3Service) {
        this.campaignRepository = campaignRepository;
        this.campaignEmailRepository = campaignEmailRepository;
        this.s3Service = s3Service;
    }

    @Override
    @Transactional
    public void run(String... args) {
        // Verificar si ya hay campañas cargadas
        if (campaignRepository.count() > 0) {
            System.out.println("Campañas ya cargadas, omitiendo precarga.");
            return;
        }

        // Crear campañas
        Campaign leadsCampaign = Campaign.builder()
                .name("Leads Campaign")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(30))
                .build();
        campaignRepository.save(leadsCampaign);

        Campaign segmentedCampaign = Campaign.builder()
                .name("Segmented Campaign")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(30))
                .build();
        campaignRepository.save(segmentedCampaign);

        // Recuperar campañas para asegurarse de que están en el contexto de persistencia
        leadsCampaign = campaignRepository.findById(leadsCampaign.getCampaignId()).orElseThrow();
        segmentedCampaign = campaignRepository.findById(segmentedCampaign.getCampaignId()).orElseThrow();

        // Obtener templates de S3
        List<String> templates = s3Service.getTemplateKeys();

        // Dividir templates en dos grupos
        int totalTemplates = templates.size();
        int mitad = totalTemplates / 2;
        List<String> leadsTemplates = templates.subList(0, mitad);
        List<String> segmentedTemplates = templates.subList(mitad, totalTemplates);

        // Cargar emails para Leads Campaign
        int step = 0;
        for (String templateKey : leadsTemplates) {
            CampaignEmail email = CampaignEmail.builder()
                    .campaign(leadsCampaign)
                    .emailStep(step++)
                    .subject("Leads Campaign - Email " + step)
                    .templateKey(templateKey)
                    .hasAttachments(false)
                    .delayDays(step)
                    .build();
            campaignEmailRepository.save(email);
        }

        // Cargar emails para Segmented Campaign
        step = 0;
        for (String templateKey : segmentedTemplates) {
            CampaignEmail email = CampaignEmail.builder()
                    .campaign(segmentedCampaign)
                    .emailStep(step++)
                    .subject("Segmented Campaign - Email " + step)
                    .templateKey(templateKey)
                    .hasAttachments(false)
                    .delayDays(step)
                    .build();
            campaignEmailRepository.save(email);
        }

        System.out.println("Datos precargados en la base de datos con dos campañas.");
    }
}
