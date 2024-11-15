package sistema.colegio.eduxsystem.Clases;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name="estudiante")
public class Estudiante {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int id;

    @NotBlank(message = "no puede estar vacío")
    public String nombre;
    @NotBlank(message = "no puede estar vacío")
    public String apellido;
    @NotBlank(message = "no puede estar vacío")
    public String dni;

    public String direccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salon_id", referencedColumnName = "id", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Salon salon;

    @Column(name = "apellido_materno")
    public String apellidoMaterno;


    @PastOrPresent(message = "La fecha de nacimiento no puede ser una fecha futura.")
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    public String sexo;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grado_id", referencedColumnName = "id", nullable = false) // 'nullable = false' para garantizar que no sea nulo
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @NotNull(message = "El grado es obligatorio") // Validación para que no sea nulo
    private Grados grados;



    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.REMOVE)
    private List<RegistroNota> notas;

}
