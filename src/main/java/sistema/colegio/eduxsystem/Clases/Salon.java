package sistema.colegio.eduxsystem.Clases;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="salon")
public class Salon {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int id;
    public String codcorrelativo;
    public String seccion;

    public String turno;
    public String nivel;
    public Integer capacidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grado_id", referencedColumnName = "id", nullable = true) // 'nullable = false' para garantizar que no sea nulo
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Grados grados;

    @OneToMany(mappedBy = "salon")
    private List<Estudiante> estudiantes;

}