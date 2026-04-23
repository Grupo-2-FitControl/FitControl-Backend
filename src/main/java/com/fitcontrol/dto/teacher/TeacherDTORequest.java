package com.fitcontrol.dto.teacher;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record TeacherDTORequest(
        @NotBlank(message = "El nombre del profesor es obligatorio")
        String name,

        @NotBlank(message = "El DNI del profesor es obligatorio")
        @Pattern(regexp = "^[0-9]{8}[A-Z]$", message = "El DNI debe tener 8 números seguidos de una letra mayúscula (ej: 12345678A)")
        String dni,

        @NotNull(message = "El año de contratación es obligatorio")
        Integer hiringYear,

        Boolean isActive,
        String imageUrl
) {}
