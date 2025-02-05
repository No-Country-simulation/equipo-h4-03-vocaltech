package com.vocaltech.api.domain.recipients;


import com.vocaltech.api.domain.campaigns.CampaignRecipient;
import com.vocaltech.api.domain.products.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "recipient_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Recipient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID recipientId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String phone;

    @Column
    private String description;

    @Column
    private Boolean MVP;

    @Column
    private Boolean hireJunior;

    @Column
    private Boolean moreInfo;

    @Column(nullable = false)
    private Boolean subscribed;

    @Column(updatable = false)
    private String audioKey;

    @Column(columnDefinition = "TEXT", updatable = false)
    private String transcription;

    @Column(columnDefinition = "TEXT", updatable = false)
    private String analysis;

    @Column(updatable = false)
    private String diagnosisPdfKey;

    @Column(updatable = false)
    private String qrCodeKey;

    @ManyToMany
    @JoinTable(
            name = "recipient_product", // Nombre único para productos relacionados a cualquier Recipient
            joinColumns = @JoinColumn(name = "recipient_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CampaignRecipient> campaignRecipients = new ArrayList<>();


    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Transient // No se persiste en la base de datos, pero lo expone como campo en el DTO
    public String getRecipientType() {
        return this.getClass().getSimpleName();
    }
}
