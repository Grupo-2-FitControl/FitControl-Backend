package com.fitcontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

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

    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "^[0-9]{8}[A-Z]$", message = "DNI inválido")
    @Column(nullable = false, unique = true, length = 9)
    private String dni;

    @NotNull(message = "El año de contratación es obligatorio")
    @Column(name = "hiring_year", nullable = false)
    private Integer hiringYear;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Activity> activities;

    public Teacher() {}

    public Teacher(Long id, String name, String dni, Integer hiringYear, Boolean isActive, String imageUrl, List<Activity> activities) {
        this.id = id;
        this.name = name;
        this.dni = dni;
        this.hiringYear = hiringYear;
        this.isActive = isActive;
        this.imageUrl = imageUrl;
        this.activities = activities;
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

    public List<Activity> getActivities() { return activities; }
    public void setActivities(List<Activity> activities) { this.activities = activities; }
}
