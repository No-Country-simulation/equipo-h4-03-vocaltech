package com.vocaltech.api.domain.campaigns;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID campaignId;

    private String name;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @OneToMany(mappedBy = "campaign")
    private List<CampaignRecipient> campaignRecipients;

    @OneToMany(mappedBy = "campaign")
    private List<CampaignEmail> campaignEmails;

}
