package com.vocaltech.api.domain.campaigns;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CampaignEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID emailId;

    @ManyToOne
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaign;

    @Column(nullable = false)
    private int emailStep; // Orden del email dentro de la campaña (1,2,3...)

    @Column(nullable = false)
    private String subject; // Asunto del email

    @Column(nullable = false)
    private String templateKey; // Ruta en AWS S3 del HTML del email

    @Column(nullable = false)
    private boolean hasAttachments; // Si el email lleva archivos adjuntos

    @Column(nullable = false)
    private int delayDays; // Cuántos días después del primer email se envía
}

