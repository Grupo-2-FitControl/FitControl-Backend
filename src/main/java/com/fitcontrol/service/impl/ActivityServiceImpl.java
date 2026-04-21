package com.fitcontrol.service.impl;

import com.fitcontrol.dto.activity.ActivityDTORequest;
import com.fitcontrol.dto.activity.ActivityDTOResponse;
import com.fitcontrol.dto.activity.ActivityMapper;
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
    public List<ActivityDTOResponse> findAll() {
        return activityRepository.findAll()
                .stream().map(ActivityMapper::entity2DTO).collect(Collectors.toList());
    }

    @Override
    public List<ActivityDTOResponse> findAllActive() {
        return activityRepository.findByIsActiveTrue()
                .stream().map(ActivityMapper::entity2DTO).collect(Collectors.toList());
    }

    @Override
    public List<ActivityDTOResponse> findFutureActivities() {
        return activityRepository.findFutureActivities(LocalDateTime.now())
                .stream().map(ActivityMapper::entity2DTO).collect(Collectors.toList());
    }

    @Override
    public List<ActivityDTOResponse> findByTeacher(Long teacherId) {
        if (!teacherRepository.existsById(teacherId))
            throw new ResourceNotFoundException("Teacher not found with id: " + teacherId);
        return activityRepository.findByTeacherId(teacherId)
                .stream().map(ActivityMapper::entity2DTO).collect(Collectors.toList());
    }

    @Override
    public ActivityDTOResponse findById(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activity not found with id: " + id));
        return ActivityMapper.entity2DTO(activity);
    }

    @Override
    public ActivityDTOResponse create(ActivityDTORequest dto) {
        Teacher teacher = teacherRepository.findById(dto.teacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + dto.teacherId()));

        if (!teacher.getIsActive())
            throw new BusinessRuleException("Cannot assign an inactive teacher to an activity");

        if (activityRepository.existsByNameAndTeacherId(dto.name(), dto.teacherId()))
            throw new DuplicateResourceException("This teacher already has an activity with that name");

        Activity activity = ActivityMapper.dto2Entity(dto, teacher);
        return ActivityMapper.entity2DTO(activityRepository.save(activity));
    }

    @Override
    public ActivityDTOResponse update(Long id, ActivityDTORequest dto) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activity not found with id: " + id));

        Teacher teacher = teacherRepository.findById(dto.teacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + dto.teacherId()));

        if (!teacher.getIsActive())
            throw new BusinessRuleException("Cannot assign an inactive teacher to an activity");

        activity.setTitle(dto.title());
        activity.setName(dto.name());
        activity.setDescription(dto.description());
        activity.setPrice(dto.price());
        activity.setImageUrl(dto.imageUrl());
        activity.setSchedule(dto.schedule());
        activity.setCapacity(dto.capacity());
        activity.setIsActive(dto.isActive());
        activity.setStartDate(dto.startDate());
        activity.setTeacher(teacher);

        return ActivityMapper.entity2DTO(activityRepository.save(activity));
    }

    @Override
    public void delete(Long id) {
        if (!activityRepository.existsById(id))
            throw new ResourceNotFoundException("Activity not found with id: " + id);
        activityRepository.deleteById(id);
    }
}
