package com.vocaltech.api.domain.products;

import com.vocaltech.api.domain.companies.Company;
import com.vocaltech.api.domain.entrepreneurs.Entrepreneur;
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
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private UUID productId;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Double price;

    @Column
    private String category;

    private Boolean active = true;

    @ManyToMany(mappedBy = "products")
    private List<Company> companies = new ArrayList<>();

    @ManyToMany(mappedBy = "products")
    private List<Entrepreneur> entrepreneurs = new ArrayList<>();


    @Column(name = "creation_date", updatable = false)
    private LocalDateTime creationDate;

    @Column(name = "actualization_date")
    private LocalDateTime actualizationDate;
}


