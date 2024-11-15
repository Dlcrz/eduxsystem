package sistema.colegio.eduxsystem.Repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sistema.colegio.eduxsystem.Clases.Notificaciones;
import sistema.colegio.eduxsystem.Clases.Salon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public interface INotificaciones extends CrudRepository<Notificaciones, Long> {

    ArrayList<Notificaciones> findByUsuario_IdAndLeidoFalse(int usuario_id);


}
