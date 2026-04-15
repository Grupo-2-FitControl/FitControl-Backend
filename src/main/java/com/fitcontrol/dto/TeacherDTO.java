@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO {
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "^[0-9]{8}[A-Z]$", message = "DNI inválido")
    private String dni;

    @NotNull(message = "El año de contratación es obligatorio")
    private Integer hiringYear;

    private Boolean isActive;
    private String imageUrl;
}