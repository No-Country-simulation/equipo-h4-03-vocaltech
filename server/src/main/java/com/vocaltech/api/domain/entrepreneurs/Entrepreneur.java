package com.vocaltech.api.domain.entrepreneurs;


import com.vocaltech.api.domain.leads.Lead;
import com.vocaltech.api.domain.products.Product;
import com.vocaltech.api.domain.recipients.Recipient;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

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
@DiscriminatorValue("Entrepreneur")
public class Entrepreneur extends Recipient {

    public enum EntrepreneurType {
        STARTUP, SMALL_BUSINESS, FREELANCE, OTHER
    }

    public enum ProductToDevelop {
        MOBILE_APP, WEB_PLATFORM, PHYSICAL_PRODUCT, OTHER
    }

    @Enumerated(EnumType.STRING)
    @Column
    private EntrepreneurType type;


    @Enumerated(EnumType.STRING)
    @Column
    private ProductToDevelop productToDevelop;


//    @Column(name = "created_at", updatable = false)
//    @CreationTimestamp
//    private LocalDateTime createdAt;

    }

