package sistema.colegio.eduxsystem.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import sistema.colegio.eduxsystem.Clases.Notificaciones;
import sistema.colegio.eduxsystem.Interfaces.INotificacionesService;
import sistema.colegio.eduxsystem.security.DetallesUsuario;

import java.security.Principal;
import java.util.List;

@ControllerAdvice
public class ControladorGlobal {
    @Autowired
    INotificacionesService INotificacionesService;
    @ModelAttribute
    public void addUserDetailsToModel(Model model, Principal principal) {
        if (principal != null) {
            UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();
            model.addAttribute("usuariodetalle", userDetails);

            // Suponiendo que el id del usuario está disponible en UserDetails
            Integer usuario_id = ((DetallesUsuario) userDetails).getId(); // Ajusta según tu implementación
            // Obtener las notificaciones del usuario
            List<Notificaciones> notificaciones = INotificacionesService.ConsultarIdNotifybyuser(usuario_id);
            model.addAttribute("notificaciones", notificaciones);
        }
    }
}
