package com.AppSenelec.mapper;

import com.AppSenelec.model.Tarif;
import com.AppSenelec.dto.CreateTarifRequest;
import com.AppSenelec.dto.CreateTarifResponse;

public class TarifMapper {
    public static Tarif toEntity(CreateTarifRequest request) {
        return Tarif.builder()
                .amount(request.getAmount())
                .kwh(request.getKwh())
                .build();
    }

    public static CreateTarifResponse toResponse(Tarif entity) {
        return CreateTarifResponse.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .kwh(entity.getKwh())
                .build();
    }
} 