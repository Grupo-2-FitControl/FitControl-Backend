package com.fitcontrol.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class TeacherDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    private String name;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 150, message = "Los apellidos no pueden superar 150 caracteres")
    private String lastName;

    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "^[0-9]{8}[A-Z]$", message = "DNI invalido")
    private String dni;

    @NotBlank(message = "La especialidad es obligatoria")
    @Size(max = 120, message = "La especialidad no puede superar 120 caracteres")
    private String specialization;

    @NotNull(message = "El ano de alta es obligatorio")
    private Integer hireYear;

    private Boolean isActive;

    @Size(max = 500, message = "La URL de la imagen no puede superar 500 caracteres")
    private String imageUrl;

    public TeacherDTO() {
    }

    public TeacherDTO(
        Long id,
        String name,
        String lastName,
        String dni,
        String specialization,
        Integer hireYear,
        Boolean isActive,
        String imageUrl
    ) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.specialization = specialization;
        this.hireYear = hireYear;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Integer getHireYear() {
        return hireYear;
    }

    public void setHireYear(Integer hireYear) {
        this.hireYear = hireYear;
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
