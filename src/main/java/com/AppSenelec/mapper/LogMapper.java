package com.AppSenelec.mapper;

import com.AppSenelec.model.Log;
import com.AppSenelec.dto.CreateLogRequest;
import com.AppSenelec.dto.CreateLogResponse;

public class LogMapper {
    public static Log toEntity(CreateLogRequest request) {
        return Log.builder()
                .action(request.getAction())
                .date(request.getDate())
                .userId(request.getUserId())
                .details(request.getDetails())
                .build();
    }

    public static CreateLogResponse toResponse(Log entity) {
        return CreateLogResponse.builder()
                .id(entity.getId())
                .action(entity.getAction())
                .date(entity.getDate())
                .userId(entity.getUserId())
                .details(entity.getDetails())
                .build();
    }
} 