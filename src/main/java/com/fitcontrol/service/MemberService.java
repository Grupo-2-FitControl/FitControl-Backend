package com.fitcontrol.service;

import com.fitcontrol.dto.MemberDTO;
import java.util.List;

public interface MemberService {
    MemberDTO createMember(MemberDTO memberDTO);

    MemberDTO getMemberById(Long id);

    List<MemberDTO> getAllMembers();

    List<MemberDTO> getActiveMembers();

    MemberDTO updateMember(Long id, MemberDTO memberDTO);

    void deleteMember(Long id);
}
