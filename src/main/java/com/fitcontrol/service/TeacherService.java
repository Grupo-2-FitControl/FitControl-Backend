package com.fitcontrol.service;

import com.fitcontrol.dto.TeacherDTO;
import java.util.List;

public interface TeacherService {
    TeacherDTO createTeacher(TeacherDTO teacherDTO);

    TeacherDTO getTeacherById(Long id);

    List<TeacherDTO> getAllTeachers();

    List<TeacherDTO> getActiveTeachers();

    TeacherDTO updateTeacher(Long id, TeacherDTO teacherDTO);

    void deleteTeacher(Long id);
}
