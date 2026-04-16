package com.fitcontrol.service;

import com.fitcontrol.dto.EnrollmentDTO;
import java.util.List;

public interface EnrollmentService {
    EnrollmentDTO createEnrollment(EnrollmentDTO enrollmentDTO);

    EnrollmentDTO getEnrollmentById(Long id);

    List<EnrollmentDTO> getAllEnrollments();

    List<EnrollmentDTO> getEnrollmentsByMember(Long memberId);

    List<EnrollmentDTO> getEnrollmentsByActivity(Long activityId);

    EnrollmentDTO updateEnrollment(Long id, EnrollmentDTO enrollmentDTO);

    void deleteEnrollment(Long id);
}
