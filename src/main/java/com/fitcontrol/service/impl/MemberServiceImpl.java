package com.fitcontrol.service.impl;

import com.fitcontrol.dto.member.MemberDTORequest;
import com.fitcontrol.dto.member.MemberDTOResponse;
import com.fitcontrol.dto.member.MemberMapper;
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
    public MemberDTOResponse createMember(MemberDTORequest dto) {
        validateRegistrationYear(dto.registrationYear());
        String normalizedDni = normalizeDni(dto.dni());

        if (memberRepository.existsByDni(normalizedDni)) {
            throw new DuplicateResourceException("Ya existe un miembro con DNI " + normalizedDni);
        }

        Member member = MemberMapper.dto2Entity(dto);
        member.setDni(normalizedDni);

        return MemberMapper.entity2DTO(memberRepository.save(member));
    }

    @Override
    public MemberDTOResponse getMemberById(Long id) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Miembro no encontrado con id " + id));
        return MemberMapper.entity2DTO(member);
    }

    @Override
    public List<MemberDTOResponse> getAllMembers() {
        return memberRepository.findAll()
            .stream()
            .map(MemberMapper::entity2DTO)
            .toList();
    }

    @Override
    public List<MemberDTOResponse> getActiveMembers() {
        return memberRepository.findByIsActiveTrue()
            .stream()
            .map(MemberMapper::entity2DTO)
            .toList();
    }

    @Override
    public MemberDTOResponse updateMember(Long id, MemberDTORequest dto) {
        validateRegistrationYear(dto.registrationYear());
        String normalizedDni = normalizeDni(dto.dni());

        Member existingMember = memberRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Miembro no encontrado con id " + id));

        boolean dniChanged = !existingMember.getDni().equals(normalizedDni);
        if (dniChanged && memberRepository.existsByDni(normalizedDni)) {
            throw new DuplicateResourceException("Ya existe un miembro con DNI " + normalizedDni);
        }

        existingMember.setName(dto.name());
        existingMember.setLastName(dto.lastName());
        existingMember.setDni(normalizedDni);
        existingMember.setRegistrationYear(dto.registrationYear());
        existingMember.setImageUrl(dto.imageUrl());
        existingMember.setMembershipType(dto.membershipType());

        if (dto.isActive() != null) {
            existingMember.setIsActive(dto.isActive());
        }

        return MemberMapper.entity2DTO(memberRepository.save(existingMember));
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

    private String normalizeDni(String dni) {
        return dni == null ? null : dni.trim().toUpperCase();
    }
}
