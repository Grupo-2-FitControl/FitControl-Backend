package com.fitcontrol.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ActivityDTO {

    private Long id;

    @NotBlank(message = "El titulo de la actividad es obligatorio")
    @Size(max = 120, message = "El titulo no puede superar 120 caracteres")
    private String title;

    @Size(max = 500, message = "La descripcion no puede superar 500 caracteres")
    private String description;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor que 0")
    private BigDecimal price;

    @NotNull(message = "La fecha es obligatoria")
    @Future(message = "La fecha de la actividad debe ser futura")
    private LocalDateTime activityDate;

    private Boolean isActive;

    @Size(max = 500, message = "La URL de la imagen no puede superar 500 caracteres")
    private String imageUrl;

    @NotNull(message = "El id del profesor es obligatorio")
    private Long teacherId;

    private String teacherName;

    public ActivityDTO() {
    }

    public ActivityDTO(
        Long id,
        String title,
        String description,
        BigDecimal price,
        LocalDateTime activityDate,
        Boolean isActive,
        String imageUrl,
        Long teacherId,
        String teacherName
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.activityDate = activityDate;
        this.isActive = isActive;
        this.imageUrl = imageUrl;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(LocalDateTime activityDate) {
        this.activityDate = activityDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
}
