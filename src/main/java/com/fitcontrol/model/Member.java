package com.fitcontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "member")
public class Member {

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

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "membership_type", length = 50)
    private String membershipType;

    @ManyToMany
    @JoinTable(
            name = "activity_member",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    @JsonIgnore
    private Set<Activity> activities = new HashSet<>();

    public Member() {}

    public Member(Long id, String name, String dni, Boolean isActive, String membershipType) {
        this.id = id;
        this.name = name;
        this.dni = dni;
        this.isActive = isActive;
        this.membershipType = membershipType;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public String getMembershipType() { return membershipType; }
    public void setMembershipType(String membershipType) { this.membershipType = membershipType; }

    public Set<Activity> getActivities() { return activities; }
    public void setActivities(Set<Activity> activities) { this.activities = activities; }
}
