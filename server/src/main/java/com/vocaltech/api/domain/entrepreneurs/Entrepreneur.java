package com.vocaltech.api.domain.entrepreneurs;


import com.vocaltech.api.domain.leads.Lead;
import com.vocaltech.api.domain.products.Product;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "entrepreneurs")
public class Entrepreneur {

    public enum EntrepreneurType {
        STARTUP, SMALL_BUSINESS, FREELANCE, OTHER
    }

    public enum ProductToDevelop {
        MOBILE_APP, WEB_PLATFORM, PHYSICAL_PRODUCT, OTHER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "entrepreneur_id")
    private UUID entrepreneurId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column
    private EntrepreneurType type;

    @Column
    private String description;

    @Column
    private Boolean MVP;

    @Enumerated(EnumType.STRING)
    @Column
    private ProductToDevelop productToDevelop;

    @Column
    private Boolean hireJunior;

    @Column
    private Boolean moreInfo;

    @Column
    private Boolean active;

    @ManyToMany
    @JoinTable(
            name = "entrepreneur_product",
            joinColumns = @JoinColumn(name = "entrepreneur_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id", nullable = true)
    private Lead lead;

    @Column(updatable = false)
    private String audioUrl;

    @Column(updatable = false)
    private String transcription;

    @Column(updatable = false)
    private String analysis;

    @Column(updatable = false)
    private String diagnosisPdfUrl;

    @Column(updatable = false)
    private String qrCodeUrl;

    @Column(name = "creation_date", updatable = false)
    private LocalDateTime creationDate;

    }

