package com.fitcontrol.service;

import com.fitcontrol.dto.member.MemberDTORequest;
import com.fitcontrol.dto.member.MemberDTOResponse;

import java.util.List;

public interface MemberService {
    MemberDTOResponse createMember(MemberDTORequest dto);
    MemberDTOResponse getMemberById(Long id);
    List<MemberDTOResponse> getAllMembers();
    List<MemberDTOResponse> getActiveMembers();
    MemberDTOResponse updateMember(Long id, MemberDTORequest dto);
    void deleteMember(Long id);
}
