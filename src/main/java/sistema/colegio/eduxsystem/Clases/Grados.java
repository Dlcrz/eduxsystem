package sistema.colegio.eduxsystem.Clases;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="grados")
public class Grados {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String nombre;
    public String descripcion;
}
