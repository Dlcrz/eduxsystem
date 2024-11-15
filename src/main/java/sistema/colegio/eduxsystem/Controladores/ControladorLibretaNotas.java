package sistema.colegio.eduxsystem.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sistema.colegio.eduxsystem.Interfaces.ICursoService;
import sistema.colegio.eduxsystem.Interfaces.IEstudianteService;
import sistema.colegio.eduxsystem.Interfaces.IGradoService;

@RequestMapping("/informesProgreso")
@Controller
public class ControladorLibretaNotas {

        String carpeta = "libretanotas/";

        @Autowired
        IEstudianteService iEstudianteService;

        @Autowired
        IGradoService iGradoService;

        @Autowired
        ICursoService iCursoService;

        @GetMapping()
        public String boletaNotas(Model model) {
            model.addAttribute("grados", iGradoService.Listar());
            model.addAttribute("curso", iCursoService.Listar());
            return carpeta + "libretaNotas";
        }
}
