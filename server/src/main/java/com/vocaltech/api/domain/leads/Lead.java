package com.vocaltech.api.domain.leads;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "leads")
public class Lead {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "lead_id")
    private UUID leadId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "creation_date", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime creationDate;

    @Column(nullable = false)
    private Boolean subscribed;
}
