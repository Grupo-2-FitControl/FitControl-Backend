package com.fitcontrol.service.impl;

import com.fitcontrol.dto.MemberDTO;
import com.fitcontrol.exception.BusinessRuleException;
import com.fitcontrol.exception.DuplicateResourceException;
import com.fitcontrol.exception.ResourceNotFoundException;
import com.fitcontrol.model.Member;
import com.fitcontrol.repository.MemberRepository;
import com.fitcontrol.service.MemberService;
import java.time.Year;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberDTO createMember(MemberDTO memberDTO) {
        validateRegistrationYear(memberDTO.getRegistrationYear());
        String normalizedDni = normalizeDni(memberDTO.getDni());

        if (memberRepository.existsByDni(normalizedDni)) {
            throw new DuplicateResourceException("Ya existe un miembro con DNI " + normalizedDni);
        }

        Member member = toEntity(memberDTO);
        member.setDni(normalizedDni);
        if (member.getIsActive() == null) {
            member.setIsActive(true);
        }

        Member savedMember = memberRepository.save(member);
        return toDto(savedMember);
    }

    @Override
    public MemberDTO getMemberById(Long id) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Miembro no encontrado con id " + id));

        return toDto(member);
    }

    @Override
    public List<MemberDTO> getAllMembers() {
        return memberRepository.findAll()
            .stream()
            .map(this::toDto)
            .toList();
    }

    @Override
    public List<MemberDTO> getActiveMembers() {
        return memberRepository.findByIsActiveTrue()
            .stream()
            .map(this::toDto)
            .toList();
    }

    @Override
    public MemberDTO updateMember(Long id, MemberDTO memberDTO) {
        validateRegistrationYear(memberDTO.getRegistrationYear());
        String normalizedDni = normalizeDni(memberDTO.getDni());

        Member existingMember = memberRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Miembro no encontrado con id " + id));

        boolean dniChanged = !existingMember.getDni().equals(normalizedDni);
        if (dniChanged && memberRepository.existsByDni(normalizedDni)) {
            throw new DuplicateResourceException("Ya existe un miembro con DNI " + normalizedDni);
        }

        existingMember.setName(memberDTO.getName());
        existingMember.setLastName(memberDTO.getLastName());
        existingMember.setDni(normalizedDni);
        existingMember.setRegistrationYear(memberDTO.getRegistrationYear());
        existingMember.setImageUrl(memberDTO.getImageUrl());

        if (memberDTO.getIsActive() != null) {
            existingMember.setIsActive(memberDTO.getIsActive());
        }

        Member savedMember = memberRepository.save(existingMember);
        return toDto(savedMember);
    }

    @Override
    public void deleteMember(Long id) {
        Member existingMember = memberRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Miembro no encontrado con id " + id));

        if (Boolean.FALSE.equals(existingMember.getIsActive())) {
            throw new BusinessRuleException("El miembro con id " + id + " ya esta inactivo");
        }

        existingMember.setIsActive(false);
        memberRepository.save(existingMember);
    }

    private void validateRegistrationYear(Integer registrationYear) {
        int currentYear = Year.now().getValue();
        if (registrationYear == null || registrationYear < 1900 || registrationYear > currentYear) {
            throw new BusinessRuleException("El ano de alta debe estar entre 1900 y " + currentYear);
        }
    }

    private MemberDTO toDto(Member member) {
        return new MemberDTO(
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

    private Member toEntity(MemberDTO memberDTO) {
        Member member = new Member();
        member.setId(memberDTO.getId());
        member.setName(memberDTO.getName());
        member.setLastName(memberDTO.getLastName());
        member.setDni(memberDTO.getDni());
        member.setRegistrationYear(memberDTO.getRegistrationYear());
        member.setIsActive(memberDTO.getIsActive());
        member.setImageUrl(memberDTO.getImageUrl());
        member.setMembershipType(memberDTO.getMembershipType());
        return member;
    }

    private String normalizeDni(String dni) {
        return dni == null ? null : dni.trim().toUpperCase();
    }
}
