package com.fitcontrol.controller;

import com.fitcontrol.dto.enrollment.EnrollmentDTOResponse;
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

    @PostMapping("/{activityId}/{userId}")
    public ResponseEntity<EnrollmentDTOResponse> enroll(@PathVariable Long activityId,
                                                        @PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentService.enroll(activityId, userId));
    }

    @DeleteMapping("/{activityId}/{userId}")
    public ResponseEntity<Void> unenroll(@PathVariable Long activityId,
                                         @PathVariable Long userId) {
        enrollmentService.unenroll(activityId, userId);
        return ResponseEntity.noContent().build();
    }
}
