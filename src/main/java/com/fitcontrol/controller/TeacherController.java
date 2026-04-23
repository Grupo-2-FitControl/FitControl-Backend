package com.fitcontrol.controller;

import com.fitcontrol.dto.TeacherDTO;
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

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public ResponseEntity<List<TeacherDTO>> getAll() {
        return ResponseEntity.ok(teacherService.findAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<TeacherDTO>> getActive() {
        return ResponseEntity.ok(teacherService.findAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TeacherDTO> create(@Valid @RequestBody TeacherDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDTO> update(@PathVariable Long id, @Valid @RequestBody TeacherDTO dto) {
        return ResponseEntity.ok(teacherService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        teacherService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
