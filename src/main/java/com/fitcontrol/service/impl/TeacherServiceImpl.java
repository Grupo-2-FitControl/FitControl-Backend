package com.fitcontrol.service.impl;

import com.fitcontrol.dto.teacher.TeacherDTORequest;
import com.fitcontrol.dto.teacher.TeacherDTOResponse;
import com.fitcontrol.dto.teacher.TeacherMapper;
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
    public List<TeacherDTOResponse> findAll() {
        return teacherRepository.findAll()
                .stream().map(TeacherMapper::entity2DTO).collect(Collectors.toList());
    }

    @Override
    public List<TeacherDTOResponse> findAllActive() {
        return teacherRepository.findByIsActiveTrue()
                .stream().map(TeacherMapper::entity2DTO).collect(Collectors.toList());
    }

    @Override
    public TeacherDTOResponse findById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado ningún profesor con id: " + id));
        return TeacherMapper.entity2DTO(teacher);
    }

    @Override
    public TeacherDTOResponse create(TeacherDTORequest dto) {
        if (teacherRepository.existsByDni(dto.dni()))
            throw new DuplicateResourceException("Ya existe un profesor registrado con el DNI " + dto.dni());

        Teacher teacher = TeacherMapper.dto2Entity(dto);
        return TeacherMapper.entity2DTO(teacherRepository.save(teacher));
    }

    @Override
    public TeacherDTOResponse update(Long id, TeacherDTORequest dto) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado ningún profesor con id: " + id));

        teacher.setName(dto.name());
        teacher.setDni(dto.dni());
        teacher.setHiringYear(dto.hiringYear());
        teacher.setIsActive(dto.isActive());
        teacher.setImageUrl(dto.imageUrl());

        return TeacherMapper.entity2DTO(teacherRepository.save(teacher));
    }

    @Override
    public void delete(Long id) {
        if (!teacherRepository.existsById(id))
            throw new ResourceNotFoundException("No se ha encontrado ningún profesor con id: " + id);
        teacherRepository.deleteById(id);
    }
}
