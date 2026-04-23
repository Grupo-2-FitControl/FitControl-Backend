package com.fitcontrol.dto.teacher;

import com.fitcontrol.model.Teacher;

public class TeacherMapper {

    public static Teacher dto2Entity(TeacherDTORequest request) {
        Teacher teacher = new Teacher();
        teacher.setName(request.name());
        teacher.setDni(request.dni());
        teacher.setHiringYear(request.hiringYear());
        teacher.setIsActive(request.isActive() != null ? request.isActive() : true);
        teacher.setImageUrl(request.imageUrl());
        return teacher;
    }

    public static TeacherDTOResponse entity2DTO(Teacher teacher) {
        return new TeacherDTOResponse(
                teacher.getId(),
                teacher.getName(),
                teacher.getDni(),
                teacher.getHiringYear(),
                teacher.getIsActive(),
                teacher.getImageUrl()
        );
    }
}
