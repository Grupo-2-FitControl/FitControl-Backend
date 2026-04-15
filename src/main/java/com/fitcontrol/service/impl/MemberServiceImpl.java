package com.fitcontrol.service.impl;

import com.fitcontrol.dto.MemberDTO;
import com.fitcontrol.exception.DuplicateResourceException;
import com.fitcontrol.exception.ResourceNotFoundException;
import com.fitcontrol.model.Member;
import com.fitcontrol.repository.MemberRepository;
import com.fitcontrol.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public List<MemberDTO> findAll() {
        return memberRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<MemberDTO> findAllActive() {
        return memberRepository.findByIsActiveTrue()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public MemberDTO findById(Long id) {
        return toDTO(memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id)));
    }

    @Override
    public MemberDTO create(MemberDTO dto) {
        if (memberRepository.existsByDni(dto.getDni()))
            throw new DuplicateResourceException("A member with DNI " + dto.getDni() + " already exists");

        Member member = toEntity(dto);
        return toDTO(memberRepository.save(member));
    }

    @Override
    public MemberDTO update(Long id, MemberDTO dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));

        if (!member.getDni().equals(dto.getDni()) && memberRepository.existsByDni(dto.getDni()))
            throw new DuplicateResourceException("A member with DNI " + dto.getDni() + " already exists");

        member.setName(dto.getName());
        member.setDni(dto.getDni());
        member.setIsActive(dto.getIsActive());
        member.setMembershipType(dto.getMembershipType());

        return toDTO(memberRepository.save(member));
    }

    @Override
    public void delete(Long id) {
        if (!memberRepository.existsById(id))
            throw new ResourceNotFoundException("Member not found with id: " + id);
        memberRepository.deleteById(id);
    }

    private MemberDTO toDTO(Member m) {
        return new MemberDTO(m.getId(), m.getName(), m.getDni(), m.getIsActive(), m.getMembershipType());
    }

    private Member toEntity(MemberDTO dto) {
        Member m = new Member();
        m.setName(dto.getName());
        m.setDni(dto.getDni());
        m.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        m.setMembershipType(dto.getMembershipType());
        return m;
    }
}
