package sistema.colegio.eduxsystem.Controladores;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sistema.colegio.eduxsystem.Clases.Curso;
import sistema.colegio.eduxsystem.Clases.Estudiante;
import sistema.colegio.eduxsystem.Clases.RegistroNota;
import sistema.colegio.eduxsystem.Clases.Salon;
import sistema.colegio.eduxsystem.Interfaces.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/estudiante/")
@Controller
public class ControladorEstudiante {

    String carpeta = "estudiante/";

    @Autowired
    IEstudianteService iEstudianteService;

    @Autowired
    ISalonService ISalonService;

    @Autowired
    INotasService notasService;

    @Autowired
    ISalonService iSalonService;

    @Autowired
    ICursoService iCursoService;

    @Autowired
    IGradoService iGradoService;

    @GetMapping("nuevoEstudiante")
    public String nuevoEstudiante(Model model) {
        model.addAttribute("salon", ISalonService.Listar());
        model.addAttribute("grado", iGradoService.Listar());
        model.addAttribute("estudiante", new Estudiante());
        return carpeta + "nuevoEstudiante";
    }
    @PostMapping("/nuevoEstudiante")
    String crearEstudiante(@Validated Estudiante estudiante, BindingResult bindingResult, RedirectAttributes ra, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("estudiante", estudiante);
            model.addAttribute("grado", iGradoService.Listar());
            return carpeta + "nuevoEstudiante";
        }

        iEstudianteService.Guardar(estudiante);

        ra.addFlashAttribute("estudiantetrue", "Estudiante añadido exitosamente");
        return ("redirect:/estudiante/listarEstudiante");
    }

    @PostMapping("registrarEstudiante")
    public String registrarEstudiante(
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("dni") String dni,
            @RequestParam("direccion") String direccion,
            @RequestParam("salon") int salonid,
            Model model) {

        List<Curso> cursos = iCursoService.Listar();

        if (iEstudianteService.existsByDni(dni)) {
            model.addAttribute("error", "Error: Ya existe el estudiante.");
            return listarEstudiante(model);
        }

        Optional<Salon> salon = iSalonService.ConsultarId(salonid);

        if (salon.isEmpty()) {
            model.addAttribute("error", "Error: No se ha seleccionado un salon válido.");
            return listarEstudiante(model);
        }

        Estudiante e = new Estudiante();
        e.setNombre(nombre);
        e.setApellido(apellido);
        e.setDni(dni);
        e.setDireccion(direccion);
        e.setSalon(salon.get());
        iEstudianteService.Guardar(e);

        // Buscar al ultimo estudiante y asignarle su registro de notas (vacio al inicio)
        int idEstudiante = iEstudianteService.obtenerUltimoID();
        Optional<Estudiante> ultimoEstudiante = iEstudianteService.ConsultarId(idEstudiante);

        if (ultimoEstudiante.isPresent()) {
            for (Curso curso : cursos) {
                RegistroNota registroNotasVacia = new RegistroNota();
                registroNotasVacia.setEstudiante(ultimoEstudiante.get());
                registroNotasVacia.setCurso(curso);

                registroNotasVacia.setNota1(0.0);
                registroNotasVacia.setNota2(0.0);
                registroNotasVacia.setNota3(0.0);
                registroNotasVacia.setNota4(0.0);
                registroNotasVacia.setPromedio(0.0);

                notasService.Guardar(registroNotasVacia);
            }

        }

        model.addAttribute("estudiantetrue", "Estudiante añadido exitosamente");
        return listarEstudiante(model);
    }

    @PostMapping("registrarEstudiantexSalon")
    public String registrarEstudiantexSalon(
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("dni") String dni,
            @RequestParam("direccion") String direccion,
            @RequestParam("salonid") int salonid,
            Model model) {
        List<Curso> cursos = iCursoService.Listar();

        if (iEstudianteService.existsByDni(dni)) {
            model.addAttribute("error", "Error: Ya existe el estudiante.");

            return listarEstudiante(model);
        }

        Optional<Salon> salon = iSalonService.ConsultarId(salonid);
        if (salon.isEmpty()) {
            model.addAttribute("error", "Error: No se ha seleccionado un salon válido.");
            return listarEstudiante(model);
        }
        Estudiante e = new Estudiante();
        e.setNombre(nombre);
        e.setApellido(apellido);
        e.setDni(dni);
        e.setDireccion(direccion);
        e.setSalon(salon.get());
        iEstudianteService.Guardar(e);

        // Buscar al ultimo estudiante y asignarle su registro de notas (vacio al inicio)
        int idEstudiante = iEstudianteService.obtenerUltimoID();
        Optional<Estudiante> ultimoEstudiante = iEstudianteService.ConsultarId(idEstudiante);

        if (ultimoEstudiante.isPresent()) {
            for (Curso curso : cursos) {
                RegistroNota registroNotasVacia = new RegistroNota();
                registroNotasVacia.setEstudiante(ultimoEstudiante.get());
                registroNotasVacia.setCurso(curso);

                registroNotasVacia.setNota1(0.0);
                registroNotasVacia.setNota2(0.0);
                registroNotasVacia.setNota3(0.0);
                registroNotasVacia.setNota4(0.0);
                registroNotasVacia.setPromedio(0.0);

                notasService.Guardar(registroNotasVacia);
            }

        }
        model.addAttribute("estudiantetrue", "Estudiante añadido exitosamente");


        return "redirect:/estudiante/listarEstudiantes?salonId=" + salonid;
    }




    @GetMapping("/listarEstudiante")
    public String listarEstudiante(Model model) {
        List<LocalDate> fechasSemana = obtenerFechasSemanaActual();
        for (LocalDate fecha : fechasSemana) {
            System.out.println("Fecha: " + fecha);
        }
        model.addAttribute("estudiantes", iEstudianteService.Listar());
        model.addAttribute("salon", ISalonService.Listar());

        return carpeta + "listaEstudiante";
    }




    private List<LocalDate> obtenerFechasSemanaActual() {
        LocalDate fechaHoy = LocalDate.now();
        LocalDate fechaInicioSemana = fechaHoy.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        List<LocalDate> fechasSemana = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            fechasSemana.add(fechaInicioSemana.plusDays(i));
        }
        return fechasSemana;
    }

    @GetMapping("eliminar")
    public String eliminarEstudiante(
            @RequestParam("cod") int id,
            Model model) {
        if (iEstudianteService.existsById(id)) {
            iEstudianteService.Eliminar(id);
            model.addAttribute("estudiantetrue", "Eliminado correctamente");
            return listarEstudiante(model);
        }

        return listarEstudiante(model);
    }

    @GetMapping("editar")
    public String editarEstudiante(
            @RequestParam("cod") int cod,
            Model model) {

        Optional<Estudiante> estudiante = iEstudianteService.ConsultarId(cod);
        model.addAttribute("estudiantes", estudiante);
        model.addAttribute("salon", ISalonService.Listar());
        return carpeta + "editarEstudiante";
    }




    @PostMapping("actualizar")
    public String actualizarEstudiante(
            @RequestParam("id") int cod,
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("dni") String dni,
            @RequestParam("direccion") String direccion,
            @RequestParam("salon") int salonid,

            Model model) {


        List<Curso> cursos = iCursoService.Listar();


        Optional<Salon> salon = iSalonService.ConsultarId(salonid);
        if (salon.isEmpty()) {
            model.addAttribute("error", "Error: No se ha seleccionado un salon válido.");
            return listarEstudiante(model);
        }

        Estudiante e = new Estudiante();
        e.setId(cod);
        e.setNombre(nombre);
        e.setApellido(apellido);
        e.setDni(dni);
        e.setDireccion(direccion);
        e.setSalon(salon.get());

        iEstudianteService.Guardar(e);

        Optional<Estudiante> estudianteactualizar = iEstudianteService.ConsultarId(cod);

        if (estudianteactualizar.isPresent()) {
            if (estudianteactualizar.get().getSalon() == null) {
                for (Curso curso : cursos) {
                    RegistroNota registroNotasVacia = new RegistroNota();
                    registroNotasVacia.setEstudiante(estudianteactualizar.get());
                    registroNotasVacia.setCurso(curso);

                    registroNotasVacia.setNota1(0.0);
                    registroNotasVacia.setNota2(0.0);
                    registroNotasVacia.setNota3(0.0);
                    registroNotasVacia.setNota4(0.0);
                    registroNotasVacia.setPromedio(0.0);

                    notasService.Guardar(registroNotasVacia);
                }

            }

        }

        model.addAttribute("estudiantetrue", "Actualización exitosa");
        return listarEstudiante(model);

    }


    @PostMapping("buscar")
    public String buscarEstudiante(@RequestParam("desc") String desc,
                                Model model) {

        List<Estudiante> estudiantes = iEstudianteService.Buscar(desc);
        model.addAttribute("estudiantes", estudiantes);
        return carpeta + "listaEstudiante";

    }


}
