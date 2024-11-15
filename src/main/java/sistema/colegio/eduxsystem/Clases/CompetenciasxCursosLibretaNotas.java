package sistema.colegio.eduxsystem.Clases;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="competencias_x_cursos_libreta_notas")
public class CompetenciasxCursosLibretaNotas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

     private String competencia;
    @ManyToOne
    @JoinColumn(name="lib_not_id", referencedColumnName="id")
    private LibretaNotas libretaNotas;

}
