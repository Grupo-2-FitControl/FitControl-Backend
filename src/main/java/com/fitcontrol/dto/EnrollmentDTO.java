package com.fitcontrol.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class EnrollmentDTO {

    private Long id;

    @NotNull(message = "El id del miembro es obligatorio")
    private Long memberId;

    private String memberName;

    @NotNull(message = "El id de la actividad es obligatorio")
    private Long activityId;

    private String activityName;

    private LocalDateTime activityDate;

    private Long teacherId;

    private String teacherName;

    private LocalDate enrollmentDate;

    @Pattern(regexp = "ACTIVE|CANCELLED|COMPLETED", message = "Estado invalido")
    private String status;

    public EnrollmentDTO() {
    }

    public EnrollmentDTO(
        Long id,
        Long memberId,
        String memberName,
        Long activityId,
        String activityName,
        LocalDateTime activityDate,
        Long teacherId,
        String teacherName,
        LocalDate enrollmentDate,
        String status
    ) {
        this.id = id;
        this.memberId = memberId;
        this.memberName = memberName;
        this.activityId = activityId;
        this.activityName = activityName;
        this.activityDate = activityDate;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.enrollmentDate = enrollmentDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public LocalDateTime getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(LocalDateTime activityDate) {
        this.activityDate = activityDate;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
