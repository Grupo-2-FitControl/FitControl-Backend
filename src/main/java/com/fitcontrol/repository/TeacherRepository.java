package com.fitcontrol.repository;

import com.fitcontrol.model.Teacher;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findByIsActiveTrue();

    boolean existsByDni(String dni);
}
