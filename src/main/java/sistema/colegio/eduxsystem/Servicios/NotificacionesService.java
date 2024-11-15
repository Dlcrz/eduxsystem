package sistema.colegio.eduxsystem.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema.colegio.eduxsystem.Clases.Notificaciones;
import sistema.colegio.eduxsystem.Interfaces.INotificacionesService;
import sistema.colegio.eduxsystem.Repositorios.INotificaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotificacionesService implements INotificacionesService {

    @Autowired
    INotificaciones data;


    @Override
    public void marcarComoLeidas(List<Notificaciones> notificaciones) {
        notificaciones.forEach(notif -> notif.setLeido(true));
        data.saveAll(notificaciones);
    }


    @Override
    public Notificaciones Guardar(Notificaciones n) {
            data.save(n);
        return n;
    }


    @Override
    public ArrayList<Notificaciones> Listar() {
        return (ArrayList<Notificaciones>) data.findAll();
    }
    @Override
    public Optional<Notificaciones> ConsultarId(Long id) {
        return data.findById(id);
    }

    @Override
    public ArrayList<Notificaciones> ConsultarIdNotifybyuser(int usuario_id) {
        return data.findByUsuario_IdAndLeidoFalse(usuario_id); // Retorna una lista
    }
}
