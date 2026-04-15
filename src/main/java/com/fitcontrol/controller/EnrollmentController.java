package com.fitcontrol.controller;

import com.fitcontrol.dto.EnrollmentDTO;
import com.fitcontrol.service.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/{activityId}/{memberId}")
    public ResponseEntity<EnrollmentDTO> enroll(@PathVariable Long activityId,
                                                @PathVariable Long memberId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentService.enroll(activityId, memberId));
    }

    @DeleteMapping("/{activityId}/{memberId}")
    public ResponseEntity<Void> unenroll(@PathVariable Long activityId,
                                         @PathVariable Long memberId) {
        enrollmentService.unenroll(activityId, memberId);
        return ResponseEntity.noContent().build();
    }
}
