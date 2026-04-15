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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final TeacherRepository teacherRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository, TeacherRepository teacherRepository) {
        this.activityRepository = activityRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<ActivityDTO> findAll() {
        return activityRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ActivityDTO> findAllActive() {
        return activityRepository.findByIsActiveTrue()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ActivityDTO> findFutureActivities() {
        return activityRepository.findFutureActivities(LocalDateTime.now())
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ActivityDTO> findByTeacher(Long teacherId) {
        if (!teacherRepository.existsById(teacherId))
            throw new ResourceNotFoundException("Teacher not found with id: " + teacherId);
        return activityRepository.findByTeacherId(teacherId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public ActivityDTO findById(Long id) {
        return toDTO(activityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activity not found with id: " + id)));
    }

    @Override
    public ActivityDTO create(ActivityDTO dto) {
        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + dto.getTeacherId()));

        if (!teacher.getIsActive())
            throw new BusinessRuleException("Cannot assign an inactive teacher to an activity");

        if (activityRepository.existsByNameAndTeacherId(dto.getName(), dto.getTeacherId()))
            throw new DuplicateResourceException("This teacher already has an activity with that name");

        Activity activity = toEntity(dto, teacher);
        return toDTO(activityRepository.save(activity));
    }

    @Override
    public ActivityDTO update(Long id, ActivityDTO dto) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activity not found with id: " + id));

        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + dto.getTeacherId()));

        if (!teacher.getIsActive())
            throw new BusinessRuleException("Cannot assign an inactive teacher to an activity");

        activity.setName(dto.getName());
        activity.setDescription(dto.getDescription());
        activity.setSchedule(dto.getSchedule());
        activity.setCapacity(dto.getCapacity());
        activity.setIsActive(dto.getIsActive());
        activity.setStartDate(dto.getStartDate());
        activity.setTeacher(teacher);

        return toDTO(activityRepository.save(activity));
    }

    @Override
    public void delete(Long id) {
        if (!activityRepository.existsById(id))
            throw new ResourceNotFoundException("Activity not found with id: " + id);
        activityRepository.deleteById(id);
    }

    private ActivityDTO toDTO(Activity a) {
        return new ActivityDTO(
                a.getId(),
                a.getName(),
                a.getDescription(),
                a.getSchedule(),
                a.getCapacity(),
                a.getIsActive(),
                a.getStartDate(),
                a.getTeacher().getId(),
                a.getTeacher().getName()
        );
    }

    private Activity toEntity(ActivityDTO dto, Teacher teacher) {
        Activity a = new Activity();
        a.setName(dto.getName());
        a.setDescription(dto.getDescription());
        a.setSchedule(dto.getSchedule());
        a.setCapacity(dto.getCapacity());
        a.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        a.setStartDate(dto.getStartDate());
        a.setTeacher(teacher);
        return a;
    }
}
