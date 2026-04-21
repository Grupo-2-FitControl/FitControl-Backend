package com.fitcontrol.dto.activity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ActivityDTOResponse(
        Long id,
        String title,
        String name,
        String description,
        BigDecimal price,
        String imageUrl,
        String schedule,
        Integer capacity,
        Boolean isActive,
        LocalDateTime startDate,
        Long teacherId,
        String teacherName
) {}
