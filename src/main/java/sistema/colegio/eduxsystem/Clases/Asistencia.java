package sistema.colegio.eduxsystem.Clases;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name="asistencia")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    public String asistencia;

    @ManyToOne
    @JoinColumn(name="estudiante_id")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name="clases_id")
    public Clases clases;
    public int getEstudianteId() {
        return estudiante.getId();
    }
}