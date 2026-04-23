package com.fitcontrol.repository;

import com.fitcontrol.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByIsActiveTrue();
    List<Activity> findByTeacherId(Long teacherId);
    boolean existsByNameAndTeacherId(String name, Long teacherId);

    @Query("SELECT a FROM Activity a WHERE a.startDate > :now")
    List<Activity> findFutureActivities(@Param("now") LocalDateTime now);

    @Query("SELECT COUNT(a) FROM Activity a JOIN a.members m WHERE m.id = :memberId AND a.startDate > :now")
    long countFutureActivitiesForMember(@Param("memberId") Long memberId, @Param("now") LocalDateTime now);

    @Query("SELECT COUNT(m) FROM Activity a JOIN a.members m WHERE a.id = :activityId")
    int countEnrolledMembers(@Param("activityId") Long activityId);
}
