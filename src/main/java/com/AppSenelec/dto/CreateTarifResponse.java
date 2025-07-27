package com.AppSenelec.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTarifResponse {
    private Long id;
    private Double amount;
    private Double kwh;
} 