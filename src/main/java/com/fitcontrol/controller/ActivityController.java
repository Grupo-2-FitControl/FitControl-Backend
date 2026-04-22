package com.fitcontrol.controller;

import com.fitcontrol.dto.activity.ActivityDTORequest;
import com.fitcontrol.dto.activity.ActivityDTOResponse;
import com.fitcontrol.dto.enrollment.EnrollmentDTOResponse;
import com.fitcontrol.dto.member.MemberDTOResponse;
import com.fitcontrol.service.ActivityService;
import com.fitcontrol.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityService activityService;
    private final EnrollmentService enrollmentService;

    public ActivityController(ActivityService activityService, EnrollmentService enrollmentService) {
        this.activityService = activityService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ResponseEntity<List<ActivityDTOResponse>> getAll() {
        return ResponseEntity.ok(activityService.findAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<ActivityDTOResponse>> getActive() {
        return ResponseEntity.ok(activityService.findAllActive());
    }

    @GetMapping("/future")
    public ResponseEntity<List<ActivityDTOResponse>> getFuture() {
        return ResponseEntity.ok(activityService.findFutureActivities());
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<ActivityDTOResponse>> getByTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok(activityService.findByTeacher(teacherId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityDTOResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(activityService.findById(id));
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<List<MemberDTOResponse>> getMembersByActivity(@PathVariable Long id) {
        return ResponseEntity.ok(enrollmentService.findMembersByActivity(id));
    }

    @PostMapping("/{id}/users/{userId}")
    public ResponseEntity<EnrollmentDTOResponse> enrollUser(@PathVariable Long id, @PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentService.enroll(id, userId));
    }

    @DeleteMapping("/{id}/users/{userId}")
    public ResponseEntity<Void> unenrollUser(@PathVariable Long id, @PathVariable Long userId) {
        enrollmentService.unenroll(id, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<ActivityDTOResponse> create(@Valid @RequestBody ActivityDTORequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(activityService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityDTOResponse> update(@PathVariable Long id, @Valid @RequestBody ActivityDTORequest dto) {
        return ResponseEntity.ok(activityService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        activityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
