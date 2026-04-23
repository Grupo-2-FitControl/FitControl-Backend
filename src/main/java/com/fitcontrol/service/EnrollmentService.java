package com.fitcontrol.service;

import com.fitcontrol.dto.ActivityDTO;
import com.fitcontrol.dto.EnrollmentDTO;
import com.fitcontrol.dto.MemberDTO;

import java.util.List;

public interface EnrollmentService {
    EnrollmentDTO enroll(Long activityId, Long userId);
    void unenroll(Long activityId, Long userId);
    List<ActivityDTO> findActivitiesByUser(Long userId);
    List<MemberDTO> findMembersByActivity(Long activityId);
    ActivityDTO enrollUserInActivity(Long userId, Long activityId);
}
