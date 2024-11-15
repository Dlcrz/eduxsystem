package sistema.colegio.eduxsystem.Clases;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="curso")
public class Curso {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int id;
    public String nombre;
    public String descripcion;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.REMOVE)
    private List<RegistroNota> notas;
}