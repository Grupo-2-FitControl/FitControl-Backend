package tu_paquete.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tu_paquete.dto.MemberDTO;
import tu_paquete.exception.DuplicateResourceException;
import tu_paquete.exception.ResourceNotFoundException;
import tu_paquete.model.Member;
import tu_paquete.repository.MemberRepository;
import tu_paquete.service.MemberService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository repository;

    @Override
    public List<MemberDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MemberDTO getById(Long id) {
        Member member = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member no encontrado"));
        return toDTO(member);
    }

    @Override
    public MemberDTO create(MemberDTO dto) {

        if (repository.existsByDni(dto.getDni())) {
            throw new DuplicateResourceException("Ya existe un miembro con ese DNI");
        }

        Member member = toEntity(dto);
        return toDTO(repository.save(member));
    }

    @Override
    public MemberDTO update(Long id, MemberDTO dto) {
        Member member = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member no encontrado"));

        member.setName(dto.getName());
        member.setLastName(dto.getLastName());
        member.setDni(dto.getDni());
        member.setRegistrationYear(dto.getRegistrationYear());
        member.setIsActive(dto.getIsActive());
        member.setImageUrl(dto.getImageUrl());

        return toDTO(repository.save(member));
    }

    @Override
    public void delete(Long id) {
        Member member = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member no encontrado"));

        repository.delete(member);
    }

    private MemberDTO toDTO(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setId(member.getId());
        dto.setName(member.getName());
        dto.setLastName(member.getLastName());
        dto.setDni(member.getDni());
        dto.setRegistrationYear(member.getRegistrationYear());
        dto.setIsActive(member.getIsActive());
        dto.setImageUrl(member.getImageUrl());
        return dto;
    }

    private Member toEntity(MemberDTO dto) {
        Member member = new Member();
        member.setName(dto.getName());
        member.setLastName(dto.getLastName());
        member.setDni(dto.getDni());
        member.setRegistrationYear(dto.getRegistrationYear());
        member.setIsActive(dto.getIsActive());
        member.setImageUrl(dto.getImageUrl());
        return member;
    }
}