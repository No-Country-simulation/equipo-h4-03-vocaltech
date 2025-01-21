package com.vocaltech.api.domain.companies;


import com.vocaltech.api.domain.products.Product;
import jakarta.persistence.*;
import lombok.*;

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
@Table(name = "companies")
public class Company {

    public enum CompanySize {
        SMALL, MEDIUM, LARGE
    }

    public enum DevelopmentStage {
        INITIAL_IDEA, DEVELOPMENT_IN_PROGRESS, IT_IS_ALREADY_TESTED, I_DONT_HAVE_A_DEFINED_MVP
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "company_id")
    private UUID companyId;

    @Column(nullable = false)
    private String companyName;

    @Column
    private String sector;

    @Enumerated(EnumType.STRING)
    @Column
    private CompanySize size;

    @Column
    private String contactName;

    @Column(nullable = false, unique = true)
    private String contactEmail;

    @Column
    private String contactPhone;

    @Column
    private String description;

    @Column
    private Boolean MVP;

    @Enumerated(EnumType.STRING)
    @Column
    private DevelopmentStage developmentStage;

    @Column
    private Boolean hireJunior;

    @ElementCollection
    private List<String> talentProfile;

    @Column
    private Boolean moreInfo;

    @Column
    private Boolean active;

    @ManyToMany
    @JoinTable(
            name = "company_product",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    @Column(name = "creation_date", updatable = false)
    private LocalDateTime creationDate;

}
