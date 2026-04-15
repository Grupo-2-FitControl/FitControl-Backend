@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final Cloudinary cloudinary;

    @Override
    public List<TeacherDTO> findAll() {
        return teacherRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<TeacherDTO> findAllActive() {
        return teacherRepository.findByIsActiveTrue()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public TeacherDTO findById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado con id: " + id));
        return toDTO(teacher);
    }

    @Override
    public TeacherDTO create(TeacherDTO dto, MultipartFile image) {
        if (teacherRepository.existsByDni(dto.getDni()))
            throw new DuplicateResourceException("Ya existe un profesor con ese DNI");

        Teacher teacher = toEntity(dto);

        if (image != null && !image.isEmpty()) {
            String url = uploadImage(image);
            teacher.setImageUrl(url);
        }
        return toDTO(teacherRepository.save(teacher));
    }

    @Override
    public TeacherDTO update(Long id, TeacherDTO dto, MultipartFile image) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado con id: " + id));

        teacher.setName(dto.getName());
        teacher.setDni(dto.getDni());
        teacher.setHiringYear(dto.getHiringYear());
        teacher.setIsActive(dto.getIsActive());

        if (image != null && !image.isEmpty()) {
            String url = uploadImage(image);
            teacher.setImageUrl(url);
        }
        return toDTO(teacherRepository.save(teacher));
    }

    @Override
    public void delete(Long id) {
        if (!teacherRepository.existsById(id))
            throw new ResourceNotFoundException("Profesor no encontrado con id: " + id);
        teacherRepository.deleteById(id);
    }

    private String uploadImage(MultipartFile image) {
        try {
            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Error al subir imagen a Cloudinary");
        }
    }

    private TeacherDTO toDTO(Teacher t) {
        return new TeacherDTO(t.getId(), t.getName(), t.getDni(),
                t.getHiringYear(), t.getIsActive(), t.getImageUrl());
    }

    private Teacher toEntity(TeacherDTO dto) {
        Teacher t = new Teacher();
        t.setName(dto.getName());
        t.setDni(dto.getDni());
        t.setHiringYear(dto.getHiringYear());
        t.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        t.setImageUrl(dto.getImageUrl());
        return t;
    }
}