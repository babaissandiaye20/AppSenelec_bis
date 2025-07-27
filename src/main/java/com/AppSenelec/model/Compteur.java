package com.AppSenelec.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.OneToMany;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Compteur extends BaseModel {
    private String referenceNumber;
    private Double kwhConsumption;
    private Long userId;
    private String status;
    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "compteur")
    private List<Achat> achats;
} 