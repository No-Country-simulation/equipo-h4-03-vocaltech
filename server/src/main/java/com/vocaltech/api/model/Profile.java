package com.vocaltech.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "profile_id")
    private UUID profileId;

    @OneToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_PROFILE_USER"))
    private User user;

    @Column
    private String country;

    @Column
    private String city;

    @Column
    private String road;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mobile_phone")
    private String mobilePhone;

}