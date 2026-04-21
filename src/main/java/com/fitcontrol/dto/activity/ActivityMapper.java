package com.fitcontrol.dto.activity;

import com.fitcontrol.model.Activity;
import com.fitcontrol.model.Teacher;

public class ActivityMapper {

    public static Activity dto2Entity(ActivityDTORequest request, Teacher teacher) {
        Activity activity = new Activity();
        activity.setTitle(request.title());
        activity.setName(request.name());
        activity.setDescription(request.description());
        activity.setPrice(request.price());
        activity.setImageUrl(request.imageUrl());
        activity.setSchedule(request.schedule());
        activity.setCapacity(request.capacity());
        activity.setIsActive(request.isActive() != null ? request.isActive() : true);
        activity.setStartDate(request.startDate());
        activity.setTeacher(teacher);
        return activity;
    }

    public static ActivityDTOResponse entity2DTO(Activity activity) {
        return new ActivityDTOResponse(
                activity.getId(),
                activity.getTitle(),
                activity.getName(),
                activity.getDescription(),
                activity.getPrice(),
                activity.getImageUrl(),
                activity.getSchedule(),
                activity.getCapacity(),
                activity.getIsActive(),
                activity.getStartDate(),
                activity.getTeacher().getId(),
                activity.getTeacher().getName()
        );
    }
}
