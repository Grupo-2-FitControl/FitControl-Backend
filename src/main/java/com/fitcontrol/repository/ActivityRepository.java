package com.fitcontrol.repository;

import com.fitcontrol.model.Activity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByIsActiveTrue();

    boolean existsByTitleIgnoreCase(String title);

    List<Activity> findByActivityDateAfterOrderByActivityDateAsc(LocalDateTime dateTime);

    List<Activity> findByTeacherId(Long teacherId);
}
