package com.fitcontrol.repository;

import com.fitcontrol.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // =========================
    // EXISTENTES
    // =========================

    List<Member> findByIsActiveTrue();

    boolean existsByDni(String dni);

    // =========================
    // FIX PRINCIPAL
    // =========================
    // Obtener miembros por actividad sin depender de derivadas de Spring Data
    @Query("""
        SELECT m
        FROM Member m
        JOIN m.activities a
        WHERE a.id = :activityId
    """)
    List<Member> findMembersByActivityId(Long activityId);
}