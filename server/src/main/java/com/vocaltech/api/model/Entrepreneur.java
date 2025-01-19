package com.vocaltech.api.model;


import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Entrepreneur {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "entrepreneur_id")
    private UUID entrepreneurId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private Integer phone;

    @Column
    private String type;

    @Column
    private String description;

    @ElementCollection
    private List<String> interestService;

    @Column
    private Boolean MVP;

    @Column
    private String productToDevelop;

    @Column
    private Boolean hireJunior;

    @Column
    private Boolean moreInfo;

    @Column
    private Boolean active;

    @Column(name = "interest_date")
    private LocalDateTime interestDate;

    }

