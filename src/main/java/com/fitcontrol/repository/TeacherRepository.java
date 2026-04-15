@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findByIsActiveTrue();
    boolean existsByDni(String dni);
}
