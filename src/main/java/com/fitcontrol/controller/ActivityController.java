package com.fitcontrol.controller;

import com.fitcontrol.dto.ActivityDTO;
import com.fitcontrol.dto.MemberDTO;
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
    public ResponseEntity<List<ActivityDTO>> getAll() {
        return ResponseEntity.ok(activityService.findAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<ActivityDTO>> getActive() {
        return ResponseEntity.ok(activityService.findAllActive());
    }

    @GetMapping("/future")
    public ResponseEntity<List<ActivityDTO>> getFuture() {
        return ResponseEntity.ok(activityService.findFutureActivities());
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<ActivityDTO>> getByTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok(activityService.findByTeacher(teacherId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(activityService.findById(id));
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<List<MemberDTO>> getMembersByActivity(@PathVariable Long id) {
        return ResponseEntity.ok(enrollmentService.findMembersByActivity(id));
    }

    @PostMapping
    public ResponseEntity<ActivityDTO> create(@Valid @RequestBody ActivityDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(activityService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityDTO> update(@PathVariable Long id, @Valid @RequestBody ActivityDTO dto) {
        return ResponseEntity.ok(activityService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        activityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
