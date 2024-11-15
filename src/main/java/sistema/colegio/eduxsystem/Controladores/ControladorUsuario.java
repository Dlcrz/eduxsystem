package sistema.colegio.eduxsystem.Controladores;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sistema.colegio.eduxsystem.Clases.Usuario;
import sistema.colegio.eduxsystem.DTO.UsuarioDTO;
import sistema.colegio.eduxsystem.Interfaces.ICursoService;
import sistema.colegio.eduxsystem.Interfaces.IUsuarioService;
import java.util.List;
import java.util.Optional;

@RequestMapping("/usuario/")
@Controller
public class ControladorUsuario {

    String carpeta = "Usuario/";

    @Autowired
    IUsuarioService iUsuarioService;

    @Autowired
    ICursoService iCursoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("nuevoUsuario")
    public String nuevoUsuario() {

        return carpeta + "nuevoUsuario";
    }


    @GetMapping("Perfil")
    public String perfilUsuario(Model model) {
        model.addAttribute("usuarios", iUsuarioService.Listar());
        model.addAttribute("urlPerfil", "/usuario/Perfil");
        return carpeta + "perfil-usuario";
    }

    @GetMapping("/listarUsuario")
    public String listarUsuario(Model model) {
        model.addAttribute("urlListarUsuario", "/usuario/listarUsuario");
        model.addAttribute("usuarios", iUsuarioService.Listar());
        return carpeta + "listaUsuario";
    }
    @PostMapping("registrarUsuario")
    public String guardarUsuario(@ModelAttribute("usuario") UsuarioDTO usuarioDto, Model model) {
        iUsuarioService.guardar(usuarioDto);
        model.addAttribute("usuariotrue", "¡Registro exitoso!");
        return listarUsuario(model);
    }

    /*
    @PostMapping("registrarUsuario")
    public String registrarUsuario(
            @RequestParam("nom") String nom,
            @RequestParam("ape") String ape,
            @RequestParam("dni") String dni,
            @RequestParam("cel") String cel,
            @RequestParam("ema") String ema,
            @RequestParam("dir") String dir,
            @RequestParam("user") String user,
            @RequestParam("password") String password,
            @RequestParam("rol") String rol,
            Model model) {


        String passwordEncoded = passwordEncoder.encode(password);

        if (iUsuarioService.existsByDni(dni)) {
            model.addAttribute("error", "Error: Ya existe el usuario.");
            return listarUsuario(model);
        }
        Usuario u = new Usuario();
        u.setNombre(nom);
        u.setApellido(ape);
        u.setDni(dni);
        u.setCelular(cel);
        u.setEmail(ema);
        u.setDireccion(dir);
        u.setUser(user);
        u.setPassword(passwordEncoded);
        u.setRol(rol);

        iUsuarioService.Guardar(u);
        model.addAttribute("usuariotrue", "Usuario añadido exitosamente");
        return listarUsuario(model);
    }
*/






    @GetMapping("eliminar")
    public String eliminarUsuario(
            @RequestParam("cod") int id,
            Model model) {
        if (iUsuarioService.existsById(id)) {
            iUsuarioService.Eliminar(id);
            model.addAttribute("usuariotrue", "Eliminado correctamente");
            return listarUsuario(model);
        }

        return listarUsuario(model);
    }

    @GetMapping("editar")
    public String editarUsuario(
            @RequestParam("cod") int cod,
            Model model) {


        Optional<Usuario> usuarios = iUsuarioService.ConsultarId(cod);
        model.addAttribute("usuario", usuarios);
        return carpeta + "editarUsuario";
    }


    @PostMapping("actualizar")
    public String actualizarUsuario(
            @RequestParam("id") int cod,
            @RequestParam("nombre") String nom,
            @RequestParam("apellido") String ape,
            @RequestParam("dni") String dni,
            @RequestParam("celular") String cel,
            @RequestParam("email") String ema,
            @RequestParam("direccion") String dir,
            @RequestParam("user") String user,
            @RequestParam("password") String password,
            @RequestParam("rol") String rol,
            Model model) {

        Usuario u = new Usuario();
        u.setId(cod);
        u.setNombre(nom);
        u.setApellido(ape);
        u.setDni(dni);
        u.setCelular(cel);
        u.setEmail(ema);
        u.setDireccion(dir);
        u.setUser(user);
        u.setPassword(password);
        u.setRol(rol);
        iUsuarioService.Guardar(u);
        model.addAttribute("usuariotrue", "Actualización exitosa");
        return listarUsuario(model);

    }


    @PostMapping("buscar")
    public String buscarUsuario(@RequestParam("desc") String desc,
                                Model model) {

        List<Usuario> usuarios = iUsuarioService.Buscar(desc);
        model.addAttribute("usuarios", usuarios);
        return carpeta + "listaUsuario";

    }

}
