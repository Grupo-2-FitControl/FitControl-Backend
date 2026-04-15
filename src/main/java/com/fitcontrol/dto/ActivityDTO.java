package com.fitcontrol.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ActivityDTO {

    private Long id;

    @NotBlank(message = "Activity name is required")
    private String name;

    private String description;

    @NotBlank(message = "Schedule is required")
    private String schedule;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    private Boolean isActive;

    private LocalDateTime startDate;

    @NotNull(message = "Teacher ID is required")
    private Long teacherId;

    private String teacherName;

    public ActivityDTO() {}

    public ActivityDTO(Long id, String name, String description, String schedule,
                       Integer capacity, Boolean isActive, LocalDateTime startDate,
                       Long teacherId, String teacherName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.schedule = schedule;
        this.capacity = capacity;
        this.isActive = isActive;
        this.startDate = startDate;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
}
