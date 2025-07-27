package com.AppSenelec.model;

import com.AppSenelec.model.TransactionStatus;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Achat extends BaseModel {
    private String numero;
    private Double amount;
    private Double kwhPurchased;
    private TransactionStatus transactionStatus;
    private LocalDateTime date;
    private String reference;
    private String codeRecharge;
    private boolean isUsed;
    @ManyToOne
    @JoinColumn(name = "compteur_id")
    private Compteur compteur;
    private Long tarifId;
} 
