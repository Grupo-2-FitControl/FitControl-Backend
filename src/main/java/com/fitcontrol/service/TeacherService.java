public interface TeacherService {
    List<TeacherDTO> findAll();
    List<TeacherDTO> findAllActive();
    TeacherDTO findById(Long id);
    TeacherDTO create(TeacherDTO dto, MultipartFile image);
    TeacherDTO update(Long id, TeacherDTO dto, MultipartFile image);
    void delete(Long id);
}