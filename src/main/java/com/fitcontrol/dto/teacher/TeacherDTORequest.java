package com.fitcontrol.dto.teacher;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record TeacherDTORequest(
        @NotBlank(message = "El nombre es obligatorio")
        String name,

        @NotBlank(message = "El DNI es obligatorio")
        @Pattern(regexp = "^[0-9]{8}[A-Z]$", message = "DNI inválido")
        String dni,

        @NotNull(message = "El año de contratación es obligatorio")
        Integer hiringYear,

        Boolean isActive,
        String imageUrl
) {}
