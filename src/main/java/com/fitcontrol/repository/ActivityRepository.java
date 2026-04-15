package com.fitcontrol.repository;

import com.fitcontrol.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByIsActiveTrue();
    List<Activity> findByTeacherId(Long teacherId);
    boolean existsByNameAndTeacherId(String name, Long teacherId);
}
