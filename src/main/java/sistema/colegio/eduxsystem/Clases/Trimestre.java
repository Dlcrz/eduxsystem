package sistema.colegio.eduxsystem.Clases;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="trimestre")
public class Trimestre {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int id;
    public String trimestre;

    @Column(columnDefinition = "YEAR")  // Configurar explícitamente como YEAR en MySQL
    public int anio;

    public Trimestre() {
        // Constructor vacío
    }

    public Trimestre(int id) {
        this.id = id;
    }

}
