package com.AppSenelec.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAchatRequest {
    @NotBlank(message = "Le numéro est obligatoire")
    private String numero;
    @NotNull(message = "Le montant est obligatoire")
    private Double amount;
    private LocalDateTime date;
    @NotBlank(message = "La référence du compteur est obligatoire")
    private String reference;
    @NotNull(message = "L'id du tarif est obligatoire")
    private Long tarifId;
} 