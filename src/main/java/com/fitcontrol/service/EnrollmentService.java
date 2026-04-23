package com.fitcontrol.service;

import com.fitcontrol.dto.activity.ActivityDTOResponse;
import com.fitcontrol.dto.enrollment.EnrollmentDTOResponse;
import com.fitcontrol.dto.member.MemberDTOResponse;

import java.util.List;

public interface EnrollmentService {
    EnrollmentDTOResponse enroll(Long activityId, Long userId);
    void unenroll(Long activityId, Long userId);
    List<ActivityDTOResponse> findActivitiesByUser(Long userId);
    List<MemberDTOResponse> findMembersByActivity(Long activityId);
}
