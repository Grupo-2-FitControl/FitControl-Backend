package com.fitcontrol.service.impl;

import com.fitcontrol.dto.ActivityDTO;
import com.fitcontrol.exception.BusinessRuleException;
import com.fitcontrol.exception.DuplicateResourceException;
import com.fitcontrol.exception.ResourceNotFoundException;
import com.fitcontrol.model.Activity;
import com.fitcontrol.model.Teacher;
import com.fitcontrol.repository.ActivityRepository;
import com.fitcontrol.repository.TeacherRepository;
import com.fitcontrol.service.ActivityService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final TeacherRepository teacherRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository, TeacherRepository teacherRepository) {
        this.activityRepository = activityRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public ActivityDTO createActivity(ActivityDTO activityDTO) {
        validateActivityDate(activityDTO.getActivityDate());

        if (activityRepository.existsByTitleIgnoreCase(activityDTO.getTitle())) {
            throw new DuplicateResourceException("Ya existe una actividad con titulo " + activityDTO.getTitle());
        }

        Teacher teacher = findActiveTeacher(activityDTO.getTeacherId());
        Activity activity = toEntity(activityDTO);
        activity.setTeacher(teacher);
        if (activity.getIsActive() == null) {
            activity.setIsActive(true);
        }

        Activity saved = activityRepository.save(activity);
        return toDto(saved);
    }

    @Override
    public ActivityDTO getActivityById(Long id) {
        Activity activity = activityRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Actividad no encontrada con id " + id));

        return toDto(activity);
    }

    @Override
    public List<ActivityDTO> getAllActivities() {
        return activityRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public List<ActivityDTO> getActiveActivities() {
        return activityRepository.findByIsActiveTrue().stream().map(this::toDto).toList();
    }

    @Override
    public List<ActivityDTO> getFutureActivities() {
        return activityRepository.findByActivityDateAfterOrderByActivityDateAsc(LocalDateTime.now())
            .stream()
            .map(this::toDto)
            .toList();
    }

    @Override
    public List<ActivityDTO> getActivitiesByTeacher(Long teacherId) {
        return activityRepository.findByTeacherId(teacherId).stream().map(this::toDto).toList();
    }

    @Override
    public ActivityDTO updateActivity(Long id, ActivityDTO activityDTO) {
        validateActivityDate(activityDTO.getActivityDate());

        Activity existing = activityRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Actividad no encontrada con id " + id));

        boolean titleChanged = !existing.getTitle().equalsIgnoreCase(activityDTO.getTitle());
        if (titleChanged && activityRepository.existsByTitleIgnoreCase(activityDTO.getTitle())) {
            throw new DuplicateResourceException("Ya existe una actividad con titulo " + activityDTO.getTitle());
        }

        Teacher teacher = findActiveTeacher(activityDTO.getTeacherId());

        existing.setTitle(activityDTO.getTitle());
        existing.setDescription(activityDTO.getDescription());
        existing.setPrice(activityDTO.getPrice());
        existing.setActivityDate(activityDTO.getActivityDate());
        existing.setImageUrl(activityDTO.getImageUrl());
        existing.setTeacher(teacher);
        if (activityDTO.getIsActive() != null) {
            existing.setIsActive(activityDTO.getIsActive());
        }

        Activity saved = activityRepository.save(existing);
        return toDto(saved);
    }

    @Override
    public void deleteActivity(Long id) {
        Activity existing = activityRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Actividad no encontrada con id " + id));

        if (Boolean.FALSE.equals(existing.getIsActive())) {
            throw new BusinessRuleException("La actividad con id " + id + " ya esta inactiva");
        }

        existing.setIsActive(false);
        activityRepository.save(existing);
    }

    private void validateActivityDate(LocalDateTime activityDate) {
        if (activityDate == null || !activityDate.isAfter(LocalDateTime.now())) {
            throw new BusinessRuleException("La fecha de la actividad debe ser futura");
        }
    }

    private ActivityDTO toDto(Activity activity) {
        return new ActivityDTO(
            activity.getId(),
            activity.getTitle(),
            activity.getDescription(),
            activity.getPrice(),
            activity.getActivityDate(),
            activity.getIsActive(),
            activity.getImageUrl(),
            activity.getTeacher().getId(),
            activity.getTeacher().getName() + " " + activity.getTeacher().getLastName()
        );
    }

    private Activity toEntity(ActivityDTO activityDTO) {
        Activity activity = new Activity();
        activity.setId(activityDTO.getId());
        activity.setTitle(activityDTO.getTitle());
        activity.setDescription(activityDTO.getDescription());
        activity.setPrice(activityDTO.getPrice());
        activity.setActivityDate(activityDTO.getActivityDate());
        activity.setIsActive(activityDTO.getIsActive());
        activity.setImageUrl(activityDTO.getImageUrl());
        return activity;
    }

    private Teacher findActiveTeacher(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
            .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado con id " + teacherId));

        if (Boolean.FALSE.equals(teacher.getIsActive())) {
            throw new BusinessRuleException("El profesor no esta dado de alta en la empresa");
        }

        return teacher;
    }
}
