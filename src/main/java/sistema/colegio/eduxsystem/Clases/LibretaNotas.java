package sistema.colegio.eduxsystem.Clases;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name="libreta_notas")
public class LibretaNotas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grado_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Grados grados;

    @Column(columnDefinition = "YEAR")
    private int anioacademico;

    @ManyToOne
    @JoinColumn(name="curso_id", referencedColumnName="id")
    private Curso curso;
}
