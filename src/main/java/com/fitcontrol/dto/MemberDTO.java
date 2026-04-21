package com.fitcontrol.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
public class MemberDTO {

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

    @NotNull(message = "El ano de alta es obligatorio")
    private Integer registrationYear;

    private Boolean isActive;

    @Size(max = 500, message = "La URL de la imagen no puede superar 500 caracteres")
    private String imageUrl;

    @Size(max = 50, message = "El tipo de membresía no puede superar 50 caracteres")
    private String membershipType;

    public MemberDTO() {
    }

    public MemberDTO(
        Long id,
        String name,
        String lastName,
        String dni,
        Integer registrationYear,
        Boolean isActive,
        String imageUrl,
        String membershipType
    ) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.registrationYear = registrationYear;
        this.isActive = isActive;
        this.imageUrl = imageUrl;
        this.membershipType = membershipType;
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

    public Integer getRegistrationYear() {
        return registrationYear;
    }

    public void setRegistrationYear(Integer registrationYear) {
        this.registrationYear = registrationYear;
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

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }
}
