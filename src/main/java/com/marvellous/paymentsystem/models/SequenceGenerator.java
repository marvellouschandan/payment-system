package com.marvellous.paymentsystem.models;

import com.marvellous.paymentsystem.models.enums.SequenceName;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SequenceGenerator {
    @Id
    String id;
    Long sequence;
}
