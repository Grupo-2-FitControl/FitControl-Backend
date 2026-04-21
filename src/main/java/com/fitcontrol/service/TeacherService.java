package com.fitcontrol.service;

import com.fitcontrol.dto.teacher.TeacherDTORequest;
import com.fitcontrol.dto.teacher.TeacherDTOResponse;

import java.util.List;

public interface TeacherService {
    List<TeacherDTOResponse> findAll();
    List<TeacherDTOResponse> findAllActive();
    TeacherDTOResponse findById(Long id);
    TeacherDTOResponse create(TeacherDTORequest dto);
    TeacherDTOResponse update(Long id, TeacherDTORequest dto);
    void delete(Long id);
}
