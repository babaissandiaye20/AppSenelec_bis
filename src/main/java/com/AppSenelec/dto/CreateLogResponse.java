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
public class CreateLogResponse {
    private Long id;
    private String action;
    private LocalDateTime date;
    private Long userId;
    private String details;
} 