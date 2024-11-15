package sistema.colegio.eduxsystem.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sistema.colegio.eduxsystem.Interfaces.ICursoService;
import sistema.colegio.eduxsystem.Interfaces.IUsuarioService;

import java.security.Principal;

@Controller
public class ControladorInicio {


    @Autowired
    UserDetailsService serviciosDetallesUsuario;

    @Autowired
    IUsuarioService iUsuarioService;

    @Autowired
    ICursoService iCursoService;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        Model model) {
        if (error != null) {
            model.addAttribute("error", "Credenciales incorrectas");
        }
        return "login";
    }

    @GetMapping("/inicio")
    public String inicio(Model model, Principal principal) {
        UserDetails detallesUsuario = serviciosDetallesUsuario.loadUserByUsername(principal.getName());
        model.addAttribute("usuario", detallesUsuario);
        model.addAttribute("usuarios", iUsuarioService.Listar());
        model.addAttribute("cursos", iCursoService.Listar());
        model.addAttribute("urlInicio", "/inicio");
        return "inicio";

    }

    /*
 @GetMapping("/error")
    public String error(){
        return "error";
 }*/
    @GetMapping("/home")
    public String home(){
        return "home";
    }




}