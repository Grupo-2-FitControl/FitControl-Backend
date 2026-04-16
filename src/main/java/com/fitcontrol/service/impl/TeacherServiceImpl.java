package com.fitcontrol.service.impl;

import com.fitcontrol.dto.TeacherDTO;
import com.fitcontrol.exception.BusinessRuleException;
import com.fitcontrol.exception.DuplicateResourceException;
import com.fitcontrol.exception.ResourceNotFoundException;
import com.fitcontrol.model.Teacher;
import com.fitcontrol.repository.TeacherRepository;
import com.fitcontrol.service.TeacherService;
import java.time.Year;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public TeacherDTO createTeacher(TeacherDTO teacherDTO) {
        validateHireYear(teacherDTO.getHireYear());

        if (teacherRepository.existsByDni(teacherDTO.getDni())) {
            throw new DuplicateResourceException("Ya existe un profesor con DNI " + teacherDTO.getDni());
        }

        Teacher teacher = toEntity(teacherDTO);
        if (teacher.getIsActive() == null) {
            teacher.setIsActive(true);
        }

        Teacher savedTeacher = teacherRepository.save(teacher);
        return toDto(savedTeacher);
    }

    @Override
    public TeacherDTO getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado con id " + id));

        return toDto(teacher);
    }

    @Override
    public List<TeacherDTO> getAllTeachers() {
        return teacherRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public List<TeacherDTO> getActiveTeachers() {
        return teacherRepository.findByIsActiveTrue().stream().map(this::toDto).toList();
    }

    @Override
    public TeacherDTO updateTeacher(Long id, TeacherDTO teacherDTO) {
        validateHireYear(teacherDTO.getHireYear());

        Teacher existingTeacher = teacherRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado con id " + id));

        boolean dniChanged = !existingTeacher.getDni().equals(teacherDTO.getDni());
        if (dniChanged && teacherRepository.existsByDni(teacherDTO.getDni())) {
            throw new DuplicateResourceException("Ya existe un profesor con DNI " + teacherDTO.getDni());
        }

        existingTeacher.setName(teacherDTO.getName());
        existingTeacher.setLastName(teacherDTO.getLastName());
        existingTeacher.setDni(teacherDTO.getDni());
        existingTeacher.setSpecialization(teacherDTO.getSpecialization());
        existingTeacher.setHireYear(teacherDTO.getHireYear());
        existingTeacher.setImageUrl(teacherDTO.getImageUrl());

        if (teacherDTO.getIsActive() != null) {
            existingTeacher.setIsActive(teacherDTO.getIsActive());
        }

        Teacher savedTeacher = teacherRepository.save(existingTeacher);
        return toDto(savedTeacher);
    }

    @Override
    public void deleteTeacher(Long id) {
        Teacher existingTeacher = teacherRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado con id " + id));

        if (Boolean.FALSE.equals(existingTeacher.getIsActive())) {
            throw new BusinessRuleException("El profesor con id " + id + " ya esta inactivo");
        }

        existingTeacher.setIsActive(false);
        teacherRepository.save(existingTeacher);
    }

    private void validateHireYear(Integer hireYear) {
        int currentYear = Year.now().getValue();
        if (hireYear == null || hireYear < 1900 || hireYear > currentYear) {
            throw new BusinessRuleException("El ano de alta debe estar entre 1900 y " + currentYear);
        }
    }

    private TeacherDTO toDto(Teacher teacher) {
        return new TeacherDTO(
            teacher.getId(),
            teacher.getName(),
            teacher.getLastName(),
            teacher.getDni(),
            teacher.getSpecialization(),
            teacher.getHireYear(),
            teacher.getIsActive(),
            teacher.getImageUrl()
        );
    }

    private Teacher toEntity(TeacherDTO teacherDTO) {
        Teacher teacher = new Teacher();
        teacher.setId(teacherDTO.getId());
        teacher.setName(teacherDTO.getName());
        teacher.setLastName(teacherDTO.getLastName());
        teacher.setDni(teacherDTO.getDni());
        teacher.setSpecialization(teacherDTO.getSpecialization());
        teacher.setHireYear(teacherDTO.getHireYear());
        teacher.setIsActive(teacherDTO.getIsActive());
        teacher.setImageUrl(teacherDTO.getImageUrl());
        return teacher;
    }
}
