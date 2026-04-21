package com.fitcontrol.dto.activity;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ActivityDTORequest(
        String title,

        @NotBlank(message = "Activity name is required")
        String name,

        String description,

        @DecimalMin(value = "0.0", message = "El precio no puede ser negativo")
        BigDecimal price,

        String imageUrl,

        @NotBlank(message = "Schedule is required")
        String schedule,

        @NotNull(message = "Capacity is required")
        @Min(value = 1, message = "Capacity must be at least 1")
        Integer capacity,

        Boolean isActive,
        LocalDateTime startDate,

        @NotNull(message = "Teacher ID is required")
        Long teacherId
) {}
