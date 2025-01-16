package com.vocaltech.api.model;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(unique = true, nullable = false)
    private String email;
}
