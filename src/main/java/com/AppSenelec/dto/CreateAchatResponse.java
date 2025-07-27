package com.AppSenelec.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAchatResponse {
    private Long id;
    private String numero;
    private Double amount;
    private Double kwhPurchased;
    private String transactionStatus;
    private LocalDateTime date;
    private String reference;
    private Long tarifId;
    private String codeRecharge;
    private boolean isUsed;
} 