package com.fitcontrol.service.impl;

import com.fitcontrol.dto.ActivityDTO;
import com.fitcontrol.exception.BusinessRuleException;
import com.fitcontrol.exception.DuplicateResourceException;
import com.fitcontrol.exception.ResourceNotFoundException;
import com.fitcontrol.model.Activity;
import com.fitcontrol.repository.ActivityRepository;
import com.fitcontrol.service.ActivityService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public ActivityDTO createActivity(ActivityDTO activityDTO) {
        validateCapacity(activityDTO.getMaxCapacity());

        if (activityRepository.existsByNameIgnoreCase(activityDTO.getName())) {
            throw new DuplicateResourceException("Ya existe una actividad con nombre " + activityDTO.getName());
        }

        Activity activity = toEntity(activityDTO);
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
    public ActivityDTO updateActivity(Long id, ActivityDTO activityDTO) {
        validateCapacity(activityDTO.getMaxCapacity());

        Activity existing = activityRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Actividad no encontrada con id " + id));

        boolean nameChanged = !existing.getName().equalsIgnoreCase(activityDTO.getName());
        if (nameChanged && activityRepository.existsByNameIgnoreCase(activityDTO.getName())) {
            throw new DuplicateResourceException("Ya existe una actividad con nombre " + activityDTO.getName());
        }

        existing.setName(activityDTO.getName());
        existing.setDescription(activityDTO.getDescription());
        existing.setMaxCapacity(activityDTO.getMaxCapacity());
        existing.setImageUrl(activityDTO.getImageUrl());
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

    private void validateCapacity(Integer maxCapacity) {
        if (maxCapacity == null || maxCapacity < 1) {
            throw new BusinessRuleException("La capacidad maxima debe ser al menos 1");
        }
    }

    private ActivityDTO toDto(Activity activity) {
        return new ActivityDTO(
            activity.getId(),
            activity.getName(),
            activity.getDescription(),
            activity.getMaxCapacity(),
            activity.getIsActive(),
            activity.getImageUrl()
        );
    }

    private Activity toEntity(ActivityDTO activityDTO) {
        Activity activity = new Activity();
        activity.setId(activityDTO.getId());
        activity.setName(activityDTO.getName());
        activity.setDescription(activityDTO.getDescription());
        activity.setMaxCapacity(activityDTO.getMaxCapacity());
        activity.setIsActive(activityDTO.getIsActive());
        activity.setImageUrl(activityDTO.getImageUrl());
        return activity;
    }
}
