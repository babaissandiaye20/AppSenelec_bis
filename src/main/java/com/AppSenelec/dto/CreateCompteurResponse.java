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
public class CreateCompteurResponse {
    private Long id;
    private String referenceNumber;
    private Double kwhConsumption;
    private Long userId;
    private String status;
    private LocalDateTime creationDate;
} 