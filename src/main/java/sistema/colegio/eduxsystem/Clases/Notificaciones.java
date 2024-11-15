package sistema.colegio.eduxsystem.Clases;

import jakarta.persistence.*;
import lombok.Data;

import java.security.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name="notificaciones")
public class Notificaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name="usuario_id", referencedColumnName="id")
    private Usuario usuario;

    public String mensaje;
    public boolean leido;
    public LocalDate fecha;

}