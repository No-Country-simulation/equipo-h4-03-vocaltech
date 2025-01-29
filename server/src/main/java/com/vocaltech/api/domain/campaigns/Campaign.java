package com.vocaltech.api.domain.campaigns;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private String templateUrl; // Guarda la URL o la ruta del archivo

    private java.sql.Date startDate;

    private java.sql.Date endDate;

    @OneToMany(mappedBy = "campaign")
    private List<CampaignRecipient> campaignRecipients;

}
