package sistema.colegio.eduxsystem.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.colegio.eduxsystem.Clases.Notificaciones;
import sistema.colegio.eduxsystem.Interfaces.INotificacionesService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/notificaciones")
public class ControladorNotificaciones {

    @Autowired
    INotificacionesService INotificacionesService;

    @PostMapping()
    public Notificaciones guardarNotificacion(@RequestBody Notificaciones n) {
        return this.INotificacionesService.Guardar(n);
    }
    //obtiene las notificaciones solo de un usuario
    @GetMapping("/usuario/{usuario_id}")
    public ResponseEntity<ArrayList<Notificaciones>> getNotificacionByUsuarioId(@PathVariable("usuario_id") Integer usuario_id) {
        ArrayList<Notificaciones> notificaciones = INotificacionesService.ConsultarIdNotifybyuser(usuario_id);

        if (!notificaciones.isEmpty()) {
            return new ResponseEntity<>(notificaciones, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // lista todas las notificaciones del sistema
    @GetMapping()
    public ArrayList<Notificaciones> ListarNotificaciones() {
        return INotificacionesService.Listar();
    }



}
