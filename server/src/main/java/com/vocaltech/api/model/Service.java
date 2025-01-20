package com.vocaltech.api.model;

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
@Table(name = "services")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "service_id")
    private UUID serviceId;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Double price;

    @Column
    private String category;

    private Boolean active = true;

    @ManyToMany(mappedBy = "services")
    private List<Company> companies = new ArrayList<>();

    @ManyToMany(mappedBy = "services")
    private List<Entrepreneur> entrepreneurs = new ArrayList<>();


    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_actualization")
    private LocalDateTime dateActualization;
}


