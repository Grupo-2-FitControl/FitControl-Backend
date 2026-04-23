package com.fitcontrol.dto.member;

import com.fitcontrol.model.Member;

public class MemberMapper {

    public static Member dto2Entity(MemberDTORequest request) {
        Member member = new Member();
        member.setName(request.name());
        member.setLastName(request.lastName());
        member.setDni(request.dni());
        member.setRegistrationYear(request.registrationYear());
        member.setIsActive(request.isActive() != null ? request.isActive() : true);
        member.setImageUrl(request.imageUrl());
        member.setMembershipType(request.membershipType());
        return member;
    }

    public static MemberDTOResponse entity2DTO(Member member) {
        return new MemberDTOResponse(
                member.getId(),
                member.getName(),
                member.getLastName(),
                member.getDni(),
                member.getRegistrationYear(),
                member.getIsActive(),
                member.getImageUrl(),
                member.getMembershipType()
        );
    }
}
