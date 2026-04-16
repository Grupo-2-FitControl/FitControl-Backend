package com.fitcontrol.repository;

import com.fitcontrol.model.Enrollment;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByMemberIdAndActivityId(Long memberId, Long activityId);

    @Query("select e from Enrollment e join fetch e.member m join fetch e.activity a join fetch a.teacher where m.id = :memberId")
    List<Enrollment> findByMemberId(@Param("memberId") Long memberId);

    @Query("select e from Enrollment e join fetch e.member m join fetch e.activity a join fetch a.teacher where a.id = :activityId")
    List<Enrollment> findByActivityId(@Param("activityId") Long activityId);

    @Query("select e from Enrollment e join fetch e.member m join fetch e.activity a join fetch a.teacher")
    List<Enrollment> findAllWithDetails();

    @Query(
        "select count(e) from Enrollment e where e.member.id = :memberId and e.status = 'ACTIVE' and e.activity.activityDate > :fromDateTime"
    )
    long countFutureActiveEnrollmentsByMember(
        @Param("memberId") Long memberId,
        @Param("fromDateTime") LocalDateTime fromDateTime
    );
}
