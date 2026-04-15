package tu_paquete.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tu_paquete.model.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByIsActiveTrue();
    boolean existsByDni(String dni);
}