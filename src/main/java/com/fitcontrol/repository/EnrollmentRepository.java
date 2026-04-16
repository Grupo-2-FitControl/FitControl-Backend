package com.fitcontrol.repository;

import com.fitcontrol.model.Enrollment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByMemberIdAndActivityId(Long memberId, Long activityId);

    List<Enrollment> findByMemberId(Long memberId);

    List<Enrollment> findByActivityId(Long activityId);
}
