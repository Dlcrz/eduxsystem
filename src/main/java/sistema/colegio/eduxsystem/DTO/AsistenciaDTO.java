package sistema.colegio.eduxsystem.DTO;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import sistema.colegio.eduxsystem.Clases.Clases;
import sistema.colegio.eduxsystem.Clases.Estudiante;

import java.time.LocalDate;
import java.util.Date;

@Data
public class AsistenciaDTO {

        public int id;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        public LocalDate fecha;
        public String asistencia;
        public String nombreEstudiante;
        public int IdClases;


    public AsistenciaDTO(int id, LocalDate fecha, String asistencia, String nombreEstudiante, int idClases) {
        this.id = id;
        this.fecha = fecha;
        this.asistencia = asistencia;
        this.nombreEstudiante = nombreEstudiante;
        IdClases = idClases;
    }
}
