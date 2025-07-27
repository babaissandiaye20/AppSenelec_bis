package com.AppSenelec.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import jakarta.annotation.Nullable;
import lombok.Builder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Log extends BaseModel {
    private String action;
    private LocalDateTime date;
    @Nullable
    private Long userId;
    private String details;
} 