package sistema.colegio.eduxsystem.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sistema.colegio.eduxsystem.Clases.*;

import sistema.colegio.eduxsystem.Interfaces.*;
import sistema.colegio.eduxsystem.Repositorios.IClases;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.TemporalAdjusters;
import java.util.*;


@RequestMapping("/salon/")
@Controller
public class ControladorSalon {

    String carpeta = "salon/";
    String carpetanota = "notas/";

    String carpetaAsistencia = "asistencias/";

    @Autowired
    ISalonService iSalonService;

    @Autowired
    IUsuarioService iUsuarioService;

    @Autowired
    IEstudianteService iEstudianteService;

    @Autowired
    INotasService notasService;

    @Autowired
    IAsistenciaService iAsistenciaService;

    @Autowired
    ICursoService iCursoService;

    @Autowired
    IGradoService iGradoService;

    @Autowired
    IClasesService iClasesService;

    @Autowired
    ITrimestreService ITrimestreService;

    @GetMapping("nuevoSalon")
    public String nuevoSalon() {
        return carpeta + "nuevoSalon";
    }

    @PostMapping("registrarSalon")
    public String registrarSalon(
            @RequestParam("codcorrelativo") String codcorrelativo,
            @RequestParam("grado") int grado,
            @RequestParam("seccion") String seccion,
            @RequestParam("turno") String turno,
            @RequestParam("nivel") String nivel,
            @RequestParam("capacidad") Integer capacidad,
            Model model) {

        if (iSalonService.existsByCodcorrelativo(codcorrelativo)) {
            model.addAttribute("error", "Error: Ya existe el Salon.");
            return listarSalon(model);
        }
        Salon s = new Salon();

        s.setCodcorrelativo(codcorrelativo);
        s.setGrados(iGradoService.ConsultarId(grado).get());
        s.setSeccion(seccion);
        s.setTurno(turno);
        s.setNivel(nivel);
        s.setCapacidad(capacidad);
        iSalonService.Guardar(s);
        model.addAttribute("salontrue", "Agregado exitosamente.");
        return listarSalon(model);
    }

    @GetMapping("listarSalon")
    public String listarSalon(Model model) {

        model.addAttribute("salon", iSalonService.Listar());
        model.addAttribute("grado", iGradoService.Listar());
        return carpeta +"listaSalon";

    }



    @GetMapping("eliminar")
    public String eliminarSalon(
            @RequestParam("cod") int id,
            Model model) {
        if (iSalonService.existsById(id)) {
            Optional<Salon> salonOptional = iSalonService.ConsultarId(id);

            if (salonOptional.isPresent()) {
                Salon salonAEliminar = salonOptional.get();

                // Obtén la lista de estudiantes asociados al Salon a través del campo salon en Estudiante
                List<Estudiante> estudiantes = salonAEliminar.getEstudiantes();

                // Establece el campo salon a null para cada estudiante
                for (Estudiante estudiante : estudiantes) {
                    estudiante.setSalon(null);
                }

                iSalonService.Eliminar(id);
                model.addAttribute("salontrue", "Eliminado correctamente");
                return listarSalon(model);
            }
        }

        return listarSalon(model);
    }


    @GetMapping("editar")
    public String editarSalon(
            @RequestParam("id") int id,
            Model model) {

        Optional<Salon> salon = iSalonService.ConsultarId(id);

        Grados grados = salon.get().getGrados();

       int gradoid = grados.getId();


        model.addAttribute("salon", salon);
        //PASAMOS LAS CLASES DE ESE SALON
        // Convertir el año a tipo Year
        int anio = LocalDate.now().getYear();
        Year year = Year.of(anio);
        // Llamar al servicio para obtener las clases filtradas
        List<Clases> clases = iClasesService.findClasesAulaPorAnio(salon.get().getId(), year);
        //List<Clases> clases = iClasesService.findClasesAula(salon.get().getId());
        List<Estudiante> estudiantes = iEstudianteService.obtenerEstudiantesPorSalon(salon.get().getId());
        model.addAttribute("estudiantes", estudiantes);
        model.addAttribute("estudiantsinasig", iEstudianteService.ObtenerEstudiantesPorGradoSalonIsNull(gradoid));
        model.addAttribute("clases", clases);
        model.addAttribute("cursos", iCursoService.Listar());
        model.addAttribute("profesores", iUsuarioService.Listar());
        return carpeta + "editarSalon";
    }



    @PostMapping("actualizar")
    public String actualizarSalon(
            @RequestParam("id") int cod,
            @RequestParam("codcorrelativo") String codcorrelativo,
            @RequestParam("seccion") String seccion,
            @RequestParam("turno") String turno,
            @RequestParam("nivel") String nivel,
            @RequestParam("capacidad") Integer capacidad, RedirectAttributes ra) {

        // Buscar el salón actual por su id

        Optional<Salon> salonOptional = iSalonService.ConsultarId(cod);

        salonOptional.ifPresent(s -> {
            s.setCodcorrelativo(codcorrelativo);
            s.setSeccion(seccion);
            s.setTurno(turno);
            s.setNivel(nivel);
            s.setCapacidad(capacidad);

            // Guardar los cambios
            iSalonService.Guardar(s);
        });

        if (salonOptional.isEmpty()) {
            ra.addFlashAttribute("error", "Salón no encontrado.");
        } else {
            ra.addFlashAttribute("salontrue", "Actualización exitosa.");
        }

        // Redirigir o listar los salones
       // return listarSalon(model);
        return ("redirect:/salon/listarSalon");
    }


    @PostMapping("buscar")
    public String buscarSalon(@RequestParam("desc") String desc,
                              Model model)
    {
        List<Salon> salon = iSalonService.Buscar(desc);
        model.addAttribute("salon", salon);
        return carpeta + "listaSalon";

    }
    //listar clases o cursos por salones

    @GetMapping("listarClasesSalon")
    public String listarClasesSalon(@RequestParam("salonId") int salonId, Model model) {

        Optional<Salon> salonOp = iSalonService.ConsultarId(salonId);

        int idsalon;
        if (salonOp.isPresent()) {
            Salon salon = salonOp.get();
            idsalon = salon.getId();
            String codcorrelativo = salon.getCodcorrelativo() + " Grado: " + salon.getGrados().getNombre() + " Sección: " + salon.getSeccion();

            model.addAttribute("codcorrelativo", codcorrelativo);
            model.addAttribute("SalonId", idsalon);

        } else {

            model.addAttribute("error", "El salón no existe");

            return carpeta + "clasesxsalon";
        }


        // Convertir el año a tipo Year
        int anio = LocalDate.now().getYear();
        Year year = Year.of(anio);
        // Llamar al servicio para obtener las clases filtradas
        List<Clases> clases = iClasesService.findClasesAulaPorAnio(idsalon, year);

        model.addAttribute("clases", clases);

        model.addAttribute("cursos", iCursoService.Listar());

        return carpeta + "clasesxsalon";
    }



    //listar cursos por salones

    @GetMapping("listarCursoSalon")
    public String listarCursonotas(@RequestParam("salonId") int salonId, Model model) {

        Optional<Salon> salonOp = iSalonService.ConsultarId(salonId);

        if (salonOp.isPresent()) {
            Salon salon = salonOp.get();
            int idsalon = salon.getId();
            String codcorrelativo = salon.getCodcorrelativo() +" Grado: "+ salon.getGrados().getNombre()+" Sección: "+ salon.getSeccion();

            model.addAttribute("codcorrelativo", codcorrelativo);
            model.addAttribute("SalonId", idsalon);

        } else {

            model.addAttribute("error", "El salón no existe");

            return carpeta +"notasxCurso";
        }
        model.addAttribute("urlListarSalon", "/salon/listarCursoSalon");

        model.addAttribute("cursos", iCursoService.Listar());

        return carpeta +"notasxCurso";
    }

///controladores de asitencia


    @GetMapping("/asistenciasdeEstudiantes")
    public String asistenciasdeEstudiantes(
            @RequestParam("salonId") int salonId,
            @RequestParam("claseId") int claseId, Model model) {

        int idsalon;
        Optional<Salon> salonO = iSalonService.ConsultarId(salonId);
        Optional<Clases> cursoOptional = iClasesService.ConsultarId(claseId);
        if (salonO.isPresent()) {
            Salon salon = salonO.get();
             idsalon = salon.getId();
            String codcorrelativo = salon.getCodcorrelativo() +" Grado: "+ salon.getGrados().getNombre()+" Sección: "+ salon.getSeccion();
            model.addAttribute("codcorrelativo", codcorrelativo);
            model.addAttribute("SalonId", idsalon);
            model.addAttribute("ClaseId", claseId);
        } else {
            // Manejo de error si el salón no existe
            model.addAttribute("error", "El salón no existe");
            return "error";
        }

        return carpetaAsistencia + "asistenciaxaula";
    }

    @GetMapping("/estudiantesXSalon")
    public String estudiantesXSalon(@RequestParam("salonId") int salonId, Model model) {

        int idsalon;
        List<Estudiante> estudiantes = iEstudianteService.obtenerEstudiantesPorSalon(salonId);
        Optional<Salon> salonO = iSalonService.ConsultarId(salonId);
        if (salonO.isPresent()) {
            Salon salon = salonO.get();
            idsalon = salon.getId();
            String codcorrelativo = salon.getCodcorrelativo() +" Grado: "+ salon.getGrados().getNombre()+" Sección: "+ salon.getSeccion();
            model.addAttribute("codcorrelativo", codcorrelativo);
            model.addAttribute("SalonId", idsalon);
        } else {
            // Manejo de error si el salón no existe
            model.addAttribute("error", "El salón no existe");
            return "error";
        }
        model.addAttribute("estudiantes", estudiantes);
        return carpeta + "listaestudiantesxsalon";

    }



    @GetMapping("notaEstudiante")
    public String notaEstudiante(Model model) {

        List<Estudiante> estudiantes = iEstudianteService.Listar();
        
        model.addAttribute("listaNotas", notasService.listarNotasConNombres());
        
        return carpeta + "notaEstudiante";
    }


    @GetMapping("notaEstudiantexClase")
    public String notaEstudiantexClase(
            @RequestParam("salonId") int salonId,
            @RequestParam("claseId") int claseId,
            Model model) {

        Optional<Salon> salonOptional = iSalonService.ConsultarId(salonId);

        Optional<Clases> cursoOptional = iClasesService.ConsultarId(claseId);
        List<Trimestre> trimestres = ITrimestreService.findByAnio(LocalDate.now().getYear());

        if (salonOptional.isPresent()) {
            Salon salon = salonOptional.get();
            int idsalon = salon.getId();
            model.addAttribute("SalonId", idsalon);

            String codcorrelativo = salon.getCodcorrelativo() +" Grado: "+ salon.getGrados().getNombre()+" Sección: "+ salon.getSeccion();


            Clases clase = cursoOptional.get();
            int idcurso = clase.getId();

            String cursonombre = clase.getCurso().getNombre();
            model.addAttribute("trimestres", trimestres);
            model.addAttribute("claseId", claseId);
            model.addAttribute("cursonombre", cursonombre);
            model.addAttribute("codcorrelativo", codcorrelativo);
        } else {
            // Manejo de error si el salón no existe
            model.addAttribute("error", "El salón no existe");
            return carpetanota + "notasxclaseYcurso";
        }

        model.addAttribute("listaNotas", notasService.listarNotasConNombresYSalonYCurso(salonId, claseId));

        return carpetanota + "notasxclaseYcurso";
    }
    @GetMapping("notaEstudiantexsalonycurso")
    public String notaEstudiantexsalonycurso(
            @RequestParam("salonId") int salonId,
            @RequestParam("cursoId") int cursoId,
            Model model) {

        Optional<Salon> salonOptional = iSalonService.ConsultarId(salonId);

        Optional<Curso> cursoOptional = iCursoService.ConsultarId(cursoId);

        if (salonOptional.isPresent()) {
            Salon salon = salonOptional.get();
            int idsalon = salon.getId();
            model.addAttribute("SalonId", idsalon);

            String codcorrelativo = salon.getCodcorrelativo() +" Grado: "+ salon.getGrados().getNombre()+" Sección: "+ salon.getSeccion();


            Curso curso = cursoOptional.get();
            int idcurso = curso.getId();

            String cursonombre = curso.getNombre();

            model.addAttribute("cursonombre", cursonombre);
            model.addAttribute("codcorrelativo", codcorrelativo);
        } else {
            // Manejo de error si el salón no existe
            model.addAttribute("error", "El salón no existe");
            return carpeta + "notaSalonEstudiante";
        }
        model.addAttribute("urlListarSalon", "/salon/notaEstudiantexsalon");

        model.addAttribute("listaNotas", notasService.listarNotasConNombresYSalonYCurso(salonId, cursoId));

        return carpeta + "notaSalonEstudiante";
    }




    @PostMapping("registro-nota/")
    public ResponseEntity<String> registroNota(
        @RequestParam("nota1") double nota1,
        @RequestParam("nota2") double nota2,
        @RequestParam("nota3") double nota3,
        @RequestParam("nota4") double nota4,
        @RequestParam("promedio") double promedio,
        @RequestParam("idEstudiante") int idEstudiante,
        @RequestParam("idRegistroNota") int idRegistroNota,
        @RequestParam("idCurso") int idCurso,
        Model model
    ) {

        Optional<Estudiante> estudiante = iEstudianteService.ConsultarId(idEstudiante);
        Optional<Curso> curso = iCursoService.ConsultarId(idCurso);

        if (estudiante.isPresent()) {

            RegistroNota nuevoRegistro = new RegistroNota();
            nuevoRegistro.setId(idRegistroNota);
            nuevoRegistro.setNota1(nota1);
            nuevoRegistro.setNota2(nota2);
            nuevoRegistro.setNota3(nota3);
            nuevoRegistro.setNota4(nota4);
            nuevoRegistro.setPromedio(promedio);

            nuevoRegistro.setEstudiante(estudiante.get());
            nuevoRegistro.setCurso(curso.get());
            notasService.Guardar(nuevoRegistro);
        }

        return ResponseEntity.ok().body("{\"message\": \"Registro exitoso\"}");

    }
}
