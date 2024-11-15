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
import sistema.colegio.eduxsystem.Clases.RegistroNota;
import sistema.colegio.eduxsystem.Interfaces.ICursoService;
import sistema.colegio.eduxsystem.Interfaces.IEstudianteService;
import sistema.colegio.eduxsystem.Interfaces.INotasService;

import java.util.List;
import java.util.Optional;

@RequestMapping("/curso/")
@Controller
public class ControladorCurso {

    String carpeta = "curso/";

    @Autowired
    ICursoService iCursoService;

    @Autowired
    INotasService notasService;

    @Autowired
    IEstudianteService iEstudianteService;

    @GetMapping("nuevoCurso")
    public String nuevoCurso() {
        return carpeta + "nuevoCurso";
    }

    @PostMapping("registrarCurso")
    public String registrarCurso(
            @RequestParam("nom") String nom,
            @RequestParam("descripcion") String descripcion,
            Model model) {

        if (iCursoService.existsByNombre(nom)) {
            model.addAttribute("error", "Error: Ya existe el curso.");
            return listarCurso(model);
        }
        Curso c = new Curso();
        c.setNombre(nom);
        c.setDescripcion(descripcion);

        iCursoService.Guardar(c);

        List<Estudiante> estudiantes = iEstudianteService.Listar();

        for (Estudiante estudiante : estudiantes) {
            RegistroNota registroNotasVacia = new RegistroNota();
            registroNotasVacia.setEstudiante(estudiante);
            registroNotasVacia.setCurso(c);

            registroNotasVacia.setNota1(0.0);
            registroNotasVacia.setNota2(0.0);
            registroNotasVacia.setNota3(0.0);
            registroNotasVacia.setNota4(0.0);
            registroNotasVacia.setPromedio(0.0);

            notasService.Guardar(registroNotasVacia);
        }
        model.addAttribute("cursotrue", "Agregado exitosamente.");
        return listarCurso(model);
    }

    @GetMapping("listarCurso")
    public String listarCurso(Model model) {

        model.addAttribute("cursos", iCursoService.Listar());
        model.addAttribute("urlListarCurso", "/curso/listarCurso");
        return carpeta +"listaCurso";
    }

    @GetMapping("eliminar")
    public String eliminarCurso(
            @RequestParam("cod") int id,
            Model model) {
        if (iCursoService.existsById(id)) {
            iCursoService.Eliminar(id);
            model.addAttribute("cursotrue", "Eliminado correctamente");
            return listarCurso(model);
        }

        return listarCurso(model);
    }

    @GetMapping("editar")
    public String editarCurso(
            @RequestParam("cod") int cod,
            Model model) {
        Optional<Curso> curso = iCursoService.ConsultarId(cod);
        model.addAttribute("curso", curso);
        return carpeta + "editarCurso";
    }


    @PostMapping("actualizar")
    public String actualizarCurso(
            @RequestParam("id") int cod,
            @RequestParam("nombre") String nom,
            @RequestParam("descripcion") String descripcion,
            Model model) {

        Curso c = new Curso();
        c.setId(cod);
        c.setNombre(nom);
        c.setDescripcion(descripcion);
        iCursoService.Guardar(c);
        model.addAttribute("cursotrue", "Actualización exitosa");
        return listarCurso(model);
    }

    @PostMapping("buscar")
    public String buscarCurso(@RequestParam("desc") String desc,
                                    Model model)
    {

        List<Curso> cursos = iCursoService.Buscar(desc);
        model.addAttribute("cursos", cursos);
        return carpeta + "listaCurso";

    }


}