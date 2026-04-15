package com.fitcontrol.service;

import com.fitcontrol.dto.ActivityDTO;

import java.util.List;

public interface ActivityService {
    List<ActivityDTO> findAll();
    List<ActivityDTO> findAllActive();
    List<ActivityDTO> findFutureActivities();
    List<ActivityDTO> findByTeacher(Long teacherId);
    ActivityDTO findById(Long id);
    ActivityDTO create(ActivityDTO dto);
    ActivityDTO update(Long id, ActivityDTO dto);
    void delete(Long id);
}
