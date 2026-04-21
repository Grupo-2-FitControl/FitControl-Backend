package com.fitcontrol.dto.enrollment;

import java.time.LocalDateTime;

public record EnrollmentDTOResponse(
        Long activityId,
        String activityName,
        String activitySchedule,
        LocalDateTime startDate,
        Long userId,
        String userName,
        Long teacherId,
        String teacherName
) {}
