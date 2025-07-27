package com.AppSenelec.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.List;
import jakarta.persistence.OneToMany;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tarif extends BaseModel {
    private Double amount;
    private Double kwh;

    @OneToMany(mappedBy = "tarifId")
    private List<Achat> achats;
} 