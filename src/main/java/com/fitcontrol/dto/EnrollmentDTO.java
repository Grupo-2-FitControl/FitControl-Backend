package com.fitcontrol.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public class EnrollmentDTO {

    private Long id;

    @NotNull(message = "El id del miembro es obligatorio")
    private Long memberId;

    private String memberName;

    @NotNull(message = "El id de la actividad es obligatorio")
    private Long activityId;

    private String activityName;

    @NotNull(message = "La fecha de inscripcion es obligatoria")
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
        LocalDate enrollmentDate,
        String status
    ) {
        this.id = id;
        this.memberId = memberId;
        this.memberName = memberName;
        this.activityId = activityId;
        this.activityName = activityName;
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
