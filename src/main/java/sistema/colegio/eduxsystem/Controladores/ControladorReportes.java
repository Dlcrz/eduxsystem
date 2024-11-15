package sistema.colegio.eduxsystem.Controladores;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sistema.colegio.eduxsystem.Interfaces.*;

@RequestMapping("/reportes/")
@Controller
public class ControladorReportes {
    String carpeta = "reportes/";

    @Autowired
    IEstudianteService iEstudianteService;

    @Autowired
    ISalonService iSalonService;


    @Autowired
    IGradoService iGradoService;

    @Autowired
    IClasesService iClasesService;

    @Autowired
    ICursoService iCursoService;

    @GetMapping("calificaciones")
    public String reporteCalificaciones(Model model) {
        model.addAttribute("estudiantes", iEstudianteService.Listar());
       // model.addAttribute("estudiantes", iEstudianteService.Listar());
        return carpeta + "reporteCalificaciones";
    }
    @GetMapping("asistencias")
    public String reporteAsistencias(Model model) {
        model.addAttribute("salones", iSalonService.Listar());
        model.addAttribute("grados", iGradoService.Listar());
        model.addAttribute("clases", iClasesService.Listar());
        // model.addAttribute("estudiantes", iEstudianteService.Listar());
        return carpeta + "reporteAsistencias";
    }
}
