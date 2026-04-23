package com.fitcontrol.controller;

import com.fitcontrol.dto.activity.ActivityDTOResponse;
import com.fitcontrol.dto.teacher.TeacherDTORequest;
import com.fitcontrol.dto.teacher.TeacherDTOResponse;
import com.fitcontrol.service.ActivityService;
import com.fitcontrol.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;
    private final ActivityService activityService;

    public TeacherController(TeacherService teacherService, ActivityService activityService) {
        this.teacherService = teacherService;
        this.activityService = activityService;
    }

    @GetMapping
    public ResponseEntity<List<TeacherDTOResponse>> getAll() {
        return ResponseEntity.ok(teacherService.findAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<TeacherDTOResponse>> getActive() {
        return ResponseEntity.ok(teacherService.findAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTOResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TeacherDTOResponse> create(@Valid @RequestBody TeacherDTORequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDTOResponse> update(@PathVariable Long id, @Valid @RequestBody TeacherDTORequest dto) {
        return ResponseEntity.ok(teacherService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        teacherService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityDTOResponse>> getActivitiesByTeacher(@PathVariable Long id) {
        return ResponseEntity.ok(activityService.findByTeacher(id));
    }
}
