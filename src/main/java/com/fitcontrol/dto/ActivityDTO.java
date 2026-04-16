package com.fitcontrol.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ActivityDTO {

    private Long id;

    @NotBlank(message = "El nombre de la actividad es obligatorio")
    @Size(max = 120, message = "El nombre no puede superar 120 caracteres")
    private String name;

    @Size(max = 500, message = "La descripcion no puede superar 500 caracteres")
    private String description;

    @Min(value = 1, message = "La capacidad maxima debe ser al menos 1")
    private Integer maxCapacity;

    private Boolean isActive;

    @Size(max = 500, message = "La URL de la imagen no puede superar 500 caracteres")
    private String imageUrl;

    public ActivityDTO() {
    }

    public ActivityDTO(Long id, String name, String description, Integer maxCapacity, Boolean isActive, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.maxCapacity = maxCapacity;
        this.isActive = isActive;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
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
}
