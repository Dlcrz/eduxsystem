package sistema.colegio.eduxsystem.Clases;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="registronota")
public class RegistroNota {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int id;
    public double nota1;
    public double nota2;
    public double nota3;
    public double nota4;
    public double promedio;

    @ManyToOne
    @JoinColumn(name="estudiante_id")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name="curso_id")
    private Curso curso;

}
