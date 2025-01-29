package com.vocaltech.api.domain.companies;


import com.vocaltech.api.domain.recipients.Recipient;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("Company")
public class Company extends Recipient {

    public enum CompanySize {
        SMALL, MEDIUM, LARGE
    }

    public enum DevelopmentStage {
        INITIAL_IDEA, DEVELOPMENT_IN_PROGRESS, IT_IS_ALREADY_TESTED, I_DONT_HAVE_A_DEFINED_MVP
    }

    @Column()
    private String companyName;

    @Column
    private String sector;

    @Enumerated(EnumType.STRING)
    @Column
    private CompanySize size;

    @Enumerated(EnumType.STRING)
    @Column
    private DevelopmentStage developmentStage;

    @ElementCollection
    private List<String> talentProfile;

    @Column(name = "diagnosis_date", updatable = false)
    @CreationTimestamp
    private LocalDateTime diagnosisDate;

}
