package com.fitcontrol.dto.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberDTORequest(
        @NotBlank(message = "El nombre del socio es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String name,

        @NotBlank(message = "Los apellidos del socio son obligatorios")
        @Size(max = 150, message = "Los apellidos no pueden superar los 150 caracteres")
        String lastName,

        @NotBlank(message = "El DNI del socio es obligatorio")
        @Pattern(regexp = "^[0-9]{8}[A-Z]$", message = "El DNI debe tener 8 números seguidos de una letra mayúscula (ej: 12345678A)")
        String dni,

        @NotNull(message = "El año de alta en el gimnasio es obligatorio")
        Integer registrationYear,

        Boolean isActive,

        @Size(max = 500, message = "La URL de la imagen no puede superar los 500 caracteres")
        String imageUrl,

        @Size(max = 50, message = "El tipo de membresía no puede superar los 50 caracteres")
        String membershipType
) {}
