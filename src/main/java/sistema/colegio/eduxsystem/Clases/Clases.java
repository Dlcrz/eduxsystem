package sistema.colegio.eduxsystem.Clases;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.Year;

@Data
@Entity
@Table(name="clases")
public class Clases {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(columnDefinition = "YEAR")
    private Year anio;
    //profesor de esa clase
    @ManyToOne
    @JoinColumn(name="profesor_id", referencedColumnName="id")
    private Usuario usuario;
    //curso de esa clase
    @ManyToOne
    @JoinColumn(name="curso_id", referencedColumnName="id")
    private Curso curso;
    //salon de esa clase
    @ManyToOne
    @JoinColumn(name="salon_id", referencedColumnName="id")
    private Salon salon;

    public Clases() {
        // Constructor vacío
    }

    public Clases(int id) {
        this.id = id;
    }

}
