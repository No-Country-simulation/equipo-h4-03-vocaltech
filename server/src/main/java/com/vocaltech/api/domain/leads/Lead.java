package com.vocaltech.api.domain.leads;

import com.vocaltech.api.domain.recipients.Recipient;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@Entity
@DiscriminatorValue("Lead")
public class Lead extends Recipient {

}
