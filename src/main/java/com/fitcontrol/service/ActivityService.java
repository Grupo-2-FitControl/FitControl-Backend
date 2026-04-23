package com.fitcontrol.service;

import com.fitcontrol.dto.activity.ActivityDTORequest;
import com.fitcontrol.dto.activity.ActivityDTOResponse;

import java.util.List;

public interface ActivityService {
    List<ActivityDTOResponse> findAll();
    List<ActivityDTOResponse> findAllActive();
    List<ActivityDTOResponse> findFutureActivities();
    List<ActivityDTOResponse> findByTeacher(Long teacherId);
    ActivityDTOResponse findById(Long id);
    ActivityDTOResponse create(ActivityDTORequest dto);
    ActivityDTOResponse update(Long id, ActivityDTORequest dto);
    void delete(Long id);
}
