package com.fitcontrol.repository;

import com.fitcontrol.model.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByIsActiveTrue();

    boolean existsByDni(String dni);
}
