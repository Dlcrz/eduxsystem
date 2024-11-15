package sistema.colegio.eduxsystem.Interfaces;

import sistema.colegio.eduxsystem.Clases.Notificaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface INotificacionesService {

    void marcarComoLeidas(List<Notificaciones> notificaciones);


    public Notificaciones Guardar(Notificaciones n);

    public ArrayList<Notificaciones> Listar();
    public Optional<Notificaciones> ConsultarId(Long id);


    public ArrayList<Notificaciones> ConsultarIdNotifybyuser(int usuario_id);
}
