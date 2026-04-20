package com.fitcontrol.dto;

import java.time.LocalDateTime;

public class EnrollmentDTO {

    private Long activityId;
    private String activityName;
    private String activitySchedule;
    private LocalDateTime startDate;
    private Long userId;
    private String userName;
    private Long teacherId;
    private String teacherName;

    public EnrollmentDTO() {}

    public EnrollmentDTO(Long activityId, String activityName, String activitySchedule,
                         LocalDateTime startDate, Long userId, String userName,
                         Long teacherId, String teacherName) {
        this.activityId = activityId;
        this.activityName = activityName;
        this.activitySchedule = activitySchedule;
        this.startDate = startDate;
        this.userId = userId;
        this.userName = userName;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
    }

    public Long getActivityId() { return activityId; }
    public void setActivityId(Long activityId) { this.activityId = activityId; }

    public String getActivityName() { return activityName; }
    public void setActivityName(String activityName) { this.activityName = activityName; }

    public String getActivitySchedule() { return activitySchedule; }
    public void setActivitySchedule(String activitySchedule) { this.activitySchedule = activitySchedule; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
}
