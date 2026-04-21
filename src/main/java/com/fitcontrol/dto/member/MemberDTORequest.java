package com.fitcontrol.dto.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberDTORequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
        String name,

        @NotBlank(message = "Los apellidos son obligatorios")
        @Size(max = 150, message = "Los apellidos no pueden superar 150 caracteres")
        String lastName,

        @NotBlank(message = "El DNI es obligatorio")
        @Pattern(regexp = "^[0-9]{8}[A-Z]$", message = "DNI invalido")
        String dni,

        @NotNull(message = "El año de alta es obligatorio")
        Integer registrationYear,

        Boolean isActive,

        @Size(max = 500, message = "La URL de la imagen no puede superar 500 caracteres")
        String imageUrl,

        @Size(max = 50, message = "El tipo de membresía no puede superar 50 caracteres")
        String membershipType
) {}
