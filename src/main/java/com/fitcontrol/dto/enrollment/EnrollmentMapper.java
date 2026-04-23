package com.fitcontrol.dto.enrollment;

import com.fitcontrol.model.Activity;
import com.fitcontrol.model.Member;

public class EnrollmentMapper {

    public static EnrollmentDTOResponse entity2DTO(Activity activity, Member member) {
        return new EnrollmentDTOResponse(
                activity.getId(),
                activity.getName(),
                activity.getSchedule(),
                activity.getStartDate(),
                member.getId(),
                member.getName(),
                activity.getTeacher().getId(),
                activity.getTeacher().getName()
        );
    }
}
