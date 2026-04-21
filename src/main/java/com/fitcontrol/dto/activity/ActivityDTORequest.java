package com.fitcontrol.dto.activity;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ActivityDTORequest(
        String title,

        @NotBlank(message = "El nombre de la actividad es obligatorio")
        String name,

        String description,

        @DecimalMin(value = "0.0", message = "El precio no puede ser negativo")
        BigDecimal price,

        String imageUrl,

        @NotBlank(message = "El horario de la actividad es obligatorio")
        String schedule,

        @NotNull(message = "El aforo de la actividad es obligatorio")
        @Min(value = 1, message = "El aforo debe ser de al menos 1 persona")
        Integer capacity,

        Boolean isActive,
        LocalDateTime startDate,

        @NotNull(message = "Debes asignar un profesor a la actividad")
        Long teacherId
) {}
