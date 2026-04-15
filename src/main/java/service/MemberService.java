package tu_paquete.service;

import tu_paquete.dto.MemberDTO;

import java.util.List;

public interface MemberService {

    List<MemberDTO> getAll();

    MemberDTO getById(Long id);

    MemberDTO create(MemberDTO memberDTO);

    MemberDTO update(Long id, MemberDTO memberDTO);

    void delete(Long id);
}