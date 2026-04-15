package com.fitcontrol.service;

import com.fitcontrol.dto.ActivityDTO;
import com.fitcontrol.dto.EnrollmentDTO;
import com.fitcontrol.dto.MemberDTO;

import java.util.List;

public interface EnrollmentService {
    EnrollmentDTO enroll(Long activityId, Long memberId);
    void unenroll(Long activityId, Long memberId);
    List<ActivityDTO> findActivitiesByMember(Long memberId);
    List<MemberDTO> findMembersByActivity(Long activityId);
}
