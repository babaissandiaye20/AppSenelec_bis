package com.AppSenelec.mapper;

import com.AppSenelec.model.Achat;
import com.AppSenelec.model.TransactionStatus;
import com.AppSenelec.dto.CreateAchatRequest;
import com.AppSenelec.dto.CreateAchatResponse;

public class AchatMapper {
    public static Achat toEntity(CreateAchatRequest request) {
        return Achat.builder()
                .numero(request.getNumero())
                .reference(request.getReference())
                .amount(request.getAmount())
                .tarifId(request.getTarifId())
                .date(request.getDate())
                .build();
    }

    public static CreateAchatResponse toResponse(Achat entity) {
        return CreateAchatResponse.builder()
                .id(entity.getId())
                .numero(entity.getNumero())
                .reference(entity.getReference())
                .amount(entity.getAmount())
                .kwhPurchased(entity.getKwhPurchased())
                .transactionStatus(entity.getTransactionStatus().name())
                .tarifId(entity.getTarifId())
                .date(entity.getDate())
                .codeRecharge(entity.getCodeRecharge())
                .isUsed(entity.isUsed())
                .build();
    }
} 