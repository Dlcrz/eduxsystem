package sistema.colegio.eduxsystem.Clases;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Year;

@Data
@Entity
@Table(name="calificaciones_trimestrales")
public class CalificacionesTrimestrales {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int id;
    public double nota1;
    public double nota2;
    public double nota3;
    public double promedio;

    @Column(columnDefinition = "YEAR")
    public int anio;

    @ManyToOne
    @JoinColumn(name="estudiante_id")
    public Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name="clases_id")
    public Clases clases;

    @ManyToOne
    @JoinColumn(name="trimestre_id")
    public Trimestre trimestre;
}
