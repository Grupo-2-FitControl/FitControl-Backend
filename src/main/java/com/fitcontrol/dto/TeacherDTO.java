package com.fitcontrol.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class TeacherDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "^[0-9]{8}[A-Z]$", message = "DNI inválido")
    private String dni;

    @NotNull(message = "El año de contratación es obligatorio")
    private Integer hiringYear;

    private Boolean isActive;
    private String imageUrl;

    public TeacherDTO() {}

    public TeacherDTO(Long id, String name, String dni, Integer hiringYear, Boolean isActive, String imageUrl) {
        this.id = id;
        this.name = name;
        this.dni = dni;
        this.hiringYear = hiringYear;
        this.isActive = isActive;
        this.imageUrl = imageUrl;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public Integer getHiringYear() { return hiringYear; }
    public void setHiringYear(Integer hiringYear) { this.hiringYear = hiringYear; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
