package com.vocaltech.api.domain.campaigns;

import com.vocaltech.api.domain.recipients.Recipient;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="campaigns_recipients")
public class CampaignRecipient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID campaignRecipientId;

    @ManyToOne
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaign;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private Recipient recipient;

    @Column(nullable = false)
    private int emailStep; // Último email enviado o próximo email en la secuencia

    @Column(nullable = false)
    private LocalDateTime nextEmailDate; // Cuándo enviar el próximo email

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        PENDING, SENT, FAILED
    }

    @PrePersist
    protected void prePersist() {
        if (status == null) {
            status = Status.PENDING;
        }
    }
}




