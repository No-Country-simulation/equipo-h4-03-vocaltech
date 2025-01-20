package com.vocaltech.api.model;


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

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "company_id")
    private UUID companyId;

    @Column(nullable = false)
    private String companyName;

    @Column
    private String sector;

    @Column
    private String size;

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

    @Column
    private String developmentStage;

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
            name = "company_service",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Service> services = new ArrayList<>();

    @Column(name = "creation_date", updatable = false)
    private LocalDateTime creationDate;

}
