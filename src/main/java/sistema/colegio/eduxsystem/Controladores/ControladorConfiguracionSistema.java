package sistema.colegio.eduxsystem.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/configuracion")
@Controller
public class ControladorConfiguracionSistema {


    String carpeta = "administracion/";
    @GetMapping()
    public String configuracionSistema(){
        return carpeta+ "configuracion";
    }
}
