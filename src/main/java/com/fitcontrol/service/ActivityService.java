package com.fitcontrol.service;

import com.fitcontrol.dto.ActivityDTO;
import java.util.List;

public interface ActivityService {
    ActivityDTO createActivity(ActivityDTO activityDTO);

    ActivityDTO getActivityById(Long id);

    List<ActivityDTO> getAllActivities();

    List<ActivityDTO> getActiveActivities();

    ActivityDTO updateActivity(Long id, ActivityDTO activityDTO);

    void deleteActivity(Long id);
}
