package sistema.colegio.eduxsystem.Controladores;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sistema.colegio.eduxsystem.Clases.Curso;
import sistema.colegio.eduxsystem.Clases.Estudiante;
import sistema.colegio.eduxsystem.Clases.Grados;
import sistema.colegio.eduxsystem.Clases.RegistroNota;
import sistema.colegio.eduxsystem.Interfaces.IGradoService;

import java.util.List;

@RequestMapping("/grado/")
@Controller
public class ControladorGrado {

    String carpeta = "grado/";

    @Autowired
    IGradoService iGradoService;

    @PostMapping()
    public String registrarGrado(
            @RequestParam("nombre") String nom,
            @RequestParam("descripcion") String descripcion,
            Model model) {

        if (iGradoService.existsByNombre(nom)) {
            model.addAttribute("error", "Error: Ya existe el grado.");
            return listarGrado(model);
        }
        Grados g = new Grados();
        g.setNombre(nom);
        g.setDescripcion(descripcion);
        iGradoService.Guardar(g);

        model.addAttribute("gradotrue", "Grado agregado exitosamente.");
        return listarGrado(model);
    }

    @GetMapping()
    public String listarGrado(Model model) {

        model.addAttribute("grados", iGradoService.Listar());

        return carpeta +"grados";
    }
    @GetMapping("eliminar")
    public String eliminarGrado(
            @RequestParam("cod") int id,
            Model model) {
        if (iGradoService.existsById(id)) {
            iGradoService.Eliminar(id);
            model.addAttribute("gradotrue", "Eliminado correctamente.");
            return listarGrado(model);
        }

        return listarGrado(model);
    }

}
