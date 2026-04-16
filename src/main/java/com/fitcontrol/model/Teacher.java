package com.fitcontrol.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Column(name = "last_name", nullable = false, length = 150)
    private String lastName;

    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "^[0-9]{8}[A-Z]$", message = "DNI invalido")
    @Column(nullable = false, unique = true, length = 9)
    private String dni;

    @NotBlank(message = "La especialidad es obligatoria")
    @Column(nullable = false, length = 120)
    private String specialization;

    @NotNull(message = "El ano de alta es obligatorio")
    @Column(name = "hire_year", nullable = false)
    private Integer hireYear;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @OneToMany(mappedBy = "teacher")
    private List<Activity> activities = new ArrayList<>();

    public Teacher() {
    }

    public Teacher(
        Long id,
        String name,
        String lastName,
        String dni,
        String specialization,
        Integer hireYear,
        Boolean isActive,
        String imageUrl,
        List<Activity> activities
    ) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.specialization = specialization;
        this.hireYear = hireYear;
        this.isActive = isActive;
        this.imageUrl = imageUrl;
        this.activities = activities;
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

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
