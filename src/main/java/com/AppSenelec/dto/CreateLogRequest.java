package com.AppSenelec.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateLogRequest {
    @NotBlank(message = "L'action est obligatoire")
    private String action;
    private LocalDateTime date;
    private Long userId;
    private String details;
} 