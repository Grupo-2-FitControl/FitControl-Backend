package com.fitcontrol.service;

import com.fitcontrol.dto.TeacherDTO;

import java.util.List;

public interface TeacherService {
    List<TeacherDTO> findAll();
    List<TeacherDTO> findAllActive();
    TeacherDTO findById(Long id);
    TeacherDTO create(TeacherDTO dto);
    TeacherDTO update(Long id, TeacherDTO dto);
    void delete(Long id);
}
