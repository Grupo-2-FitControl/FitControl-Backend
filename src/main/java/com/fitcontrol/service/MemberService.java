package com.fitcontrol.service;

import com.fitcontrol.dto.MemberDTO;

import java.util.List;

public interface MemberService {
    List<MemberDTO> findAll();
    List<MemberDTO> findAllActive();
    MemberDTO findById(Long id);
    MemberDTO create(MemberDTO dto);
    MemberDTO update(Long id, MemberDTO dto);
    void delete(Long id);
}
