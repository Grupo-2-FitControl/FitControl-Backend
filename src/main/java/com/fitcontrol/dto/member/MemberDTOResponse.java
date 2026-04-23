package com.fitcontrol.dto.member;

public record MemberDTOResponse(
        Long id,
        String name,
        String lastName,
        String dni,
        Integer registrationYear,
        Boolean isActive,
        String imageUrl,
        String membershipType
) {}
