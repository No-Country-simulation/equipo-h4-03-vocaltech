package com.vocaltech.api.domain.campaigns;

import com.vocaltech.api.domain.recipients.Recipient;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="campaigns-recipients")
public class CampaignRecipient {

    public enum Status {
        PENDING, SENT, FAILED, OTHER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaign;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private Recipient recipient;

    private java.sql.Timestamp sentDate;

    @Enumerated(EnumType.STRING)
    @Column
    private Status status = Status.PENDING;


}
