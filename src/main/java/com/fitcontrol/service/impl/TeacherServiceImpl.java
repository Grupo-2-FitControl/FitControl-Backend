package com.fitcontrol.service.impl;

import com.fitcontrol.dto.TeacherDTO;
import com.fitcontrol.exception.DuplicateResourceException;
import com.fitcontrol.exception.ResourceNotFoundException;
import com.fitcontrol.model.Teacher;
import com.fitcontrol.repository.TeacherRepository;
import com.fitcontrol.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<TeacherDTO> findAll() {
        return teacherRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<TeacherDTO> findAllActive() {
        return teacherRepository.findByIsActiveTrue()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public TeacherDTO findById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + id));
        return toDTO(teacher);
    }

    @Override
    public TeacherDTO create(TeacherDTO dto) {
        if (teacherRepository.existsByDni(dto.getDni()))
            throw new DuplicateResourceException("A teacher with that DNI already exists");

        Teacher teacher = toEntity(dto);
        return toDTO(teacherRepository.save(teacher));
    }

    @Override
    public TeacherDTO update(Long id, TeacherDTO dto) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + id));

        teacher.setName(dto.getName());
        teacher.setDni(dto.getDni());
        teacher.setHiringYear(dto.getHiringYear());
        teacher.setIsActive(dto.getIsActive());
        teacher.setImageUrl(dto.getImageUrl());

        return toDTO(teacherRepository.save(teacher));
    }

    @Override
    public void delete(Long id) {
        if (!teacherRepository.existsById(id))
            throw new ResourceNotFoundException("Teacher not found with id: " + id);
        teacherRepository.deleteById(id);
    }

    private TeacherDTO toDTO(Teacher t) {
        return new TeacherDTO(t.getId(), t.getName(), t.getDni(),
                t.getHiringYear(), t.getIsActive(), t.getImageUrl());
    }

    private Teacher toEntity(TeacherDTO dto) {
        Teacher t = new Teacher();
        t.setName(dto.getName());
        t.setDni(dto.getDni());
        t.setHiringYear(dto.getHiringYear());
        t.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        t.setImageUrl(dto.getImageUrl());
        return t;
    }
}
