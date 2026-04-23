package com.fitcontrol.dto.teacher;

public record TeacherDTOResponse(
        Long id,
        String name,
        String dni,
        Integer hiringYear,
        Boolean isActive,
        String imageUrl
) {}
