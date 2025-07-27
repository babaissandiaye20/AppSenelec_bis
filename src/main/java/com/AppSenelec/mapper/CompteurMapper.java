package com.AppSenelec.mapper;

import com.AppSenelec.model.Compteur;
import com.AppSenelec.dto.CreateCompteurRequest;
import com.AppSenelec.dto.CreateCompteurResponse;

public class CompteurMapper {
    public static Compteur toEntity(CreateCompteurRequest request) {
        return Compteur.builder()
                .referenceNumber(request.getReferenceNumber())
                .userId(request.getUserId())
                .build();
    }

    public static CreateCompteurResponse toResponse(Compteur entity) {
        return CreateCompteurResponse.builder()
                .id(entity.getId())
                .referenceNumber(entity.getReferenceNumber())
                .kwhConsumption(entity.getKwhConsumption())
                .userId(entity.getUserId())
                .status(entity.getStatus())
                .creationDate(entity.getCreationDate())
                .build();
    }
} 