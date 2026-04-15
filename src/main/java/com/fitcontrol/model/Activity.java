package com.fitcontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Activity name is required")
    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 255)
    private String description;

    @NotBlank(message = "Schedule is required")
    @Column(nullable = false, length = 100)
    private String schedule;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    @Column(nullable = false)
    private Integer capacity;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @ManyToMany(mappedBy = "activities")
    @JsonIgnore
    private Set<Member> members = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    public Activity() {}

    public Activity(Long id, String name, String description, String schedule,
                    Integer capacity, Boolean isActive, LocalDateTime startDate, Teacher teacher) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.schedule = schedule;
        this.capacity = capacity;
        this.isActive = isActive;
        this.startDate = startDate;
        this.teacher = teacher;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public Set<Member> getMembers() { return members; }
    public void setMembers(Set<Member> members) { this.members = members; }

    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Activity)) return false;
        Activity other = (Activity) o;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
