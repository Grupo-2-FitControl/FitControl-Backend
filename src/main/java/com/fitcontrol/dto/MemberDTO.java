package com.fitcontrol.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class MemberDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "^[0-9]{8}[A-Z]$", message = "DNI inválido")
    private String dni;

    private Boolean isActive;

    private String membershipType;

    public MemberDTO() {}

    public MemberDTO(Long id, String name, String dni, Boolean isActive, String membershipType) {
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
}
