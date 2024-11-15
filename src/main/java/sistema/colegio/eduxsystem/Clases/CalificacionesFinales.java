package sistema.colegio.eduxsystem.Clases;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="calificaciones_finales")
public class CalificacionesFinales {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int id;
    public double trimestre1;
    public double trimestre2;
    public double trimestre3;
    public double promediofinal;

    @Column(columnDefinition = "YEAR")
    public int anio;

    @ManyToOne
    @JoinColumn(name="estudiante_id")
    public Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name="clases_id")
    public Clases clases;

}
