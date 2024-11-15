package sistema.colegio.eduxsystem.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sistema.colegio.eduxsystem.Clases.Curso;
import sistema.colegio.eduxsystem.Clases.Trimestre;
import sistema.colegio.eduxsystem.Interfaces.ITrimestreService;

@RequestMapping("/trimestre")
@Controller
public class ControladorTrimestre {

    String carpeta = "trimestre/";

    @Autowired
    ITrimestreService iTrimestreService;

    @GetMapping()
    public String listarTrimestres(Model model) {
        model.addAttribute("trimestres", iTrimestreService.Listar());
        return carpeta +"trimestres";
    }

    @PostMapping("registrarTrimestre")
    public String registrarTrimestre(
            @RequestParam("trimestre") String trimestre,
            @RequestParam("anio") int anio,
            Model model) {

        if (iTrimestreService.existsByTrimestreAndAnio(trimestre, anio)) {
            model.addAttribute("error", "Error: Ya existe el trimestre.");
            return listarTrimestres(model);
        }
        Trimestre t = new Trimestre();
        t.setTrimestre(trimestre);
        t.setAnio(anio);
        iTrimestreService.Guardar(t);
        model.addAttribute("trimestretrue", "Agregado exitosamente.");
        return listarTrimestres(model);
    }


    @GetMapping("/eliminar")
    public String eliminarTrimestre(
            @RequestParam("cod") int id,
            Model model) {
        if (iTrimestreService.existsById(id)) {
            iTrimestreService.Eliminar(id);
            model.addAttribute("trimestretrue", "Trimestre eliminado correctamente");
            return listarTrimestres(model);
        }

        return listarTrimestres(model);
    }

}
