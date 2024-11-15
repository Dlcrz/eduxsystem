package sistema.colegio.eduxsystem.Clases;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name="configuracion_sistema")
public class ConfiguracionSistema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String codsistema;
    @NotBlank(message = "no puede estar vacío")
    @Column(name = "habilitar_envio_correo")
    private String habilitarEnvioCorreo;
    @Column(name = "usuario_correo")
    private String usuarioCorreo;
    @Column(name = "contrasenia_correo")
    private String contraseniaCorreo;
    private String servidorsmtp;
    private Integer puertosmtp;


    private String logocolegio; // Almacena solo el nombre del archivo
    private String logoestado;  // Almacena solo el nombre del archivo

    @NotBlank(message = "no puede estar vacío")
    private String nombrecolegio;
    private String textocopyright;
    private String telefono;
    private String correodirector;
    private String ugel;

}
