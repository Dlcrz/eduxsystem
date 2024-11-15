package sistema.colegio.eduxsystem.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.colegio.eduxsystem.Clases.CalificacionesTrimestrales;
import sistema.colegio.eduxsystem.Clases.Clases;
import sistema.colegio.eduxsystem.Clases.Estudiante;
import sistema.colegio.eduxsystem.Clases.Trimestre;
import sistema.colegio.eduxsystem.DTO.CalificacionesDTO;
import sistema.colegio.eduxsystem.Interfaces.ICalificacionesTrimestralesService;
import sistema.colegio.eduxsystem.Interfaces.IEstudianteService;
import sistema.colegio.eduxsystem.Interfaces.INotificacionesService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/calificaciones")
public class ControladorCalificaciones {

    @Autowired
    private ICalificacionesTrimestralesService iCalificacionesTrimestralesService;

    @Autowired
    private IEstudianteService iEstudianteService;

    @GetMapping("/{idClase}/{idTrimestre}")
    public ResponseEntity<List<CalificacionesTrimestrales>> obtenerCalificacionesPorTrimestreYClases(
            @PathVariable("idClase") int idClase,
            @PathVariable("idTrimestre") int idTrimestre) {

        List<CalificacionesTrimestrales> calificaciones = iCalificacionesTrimestralesService.findCalificacionesTrimandClases(idClase, idTrimestre);

        if (calificaciones.isEmpty()) {
            return ResponseEntity.notFound().build(); // Devuelve 404 si no se encuentran calificaciones
        }

        return ResponseEntity.ok(calificaciones); // Devuelve la lista de calificaciones con 200 OK
    }



    @GetMapping("/n/{idClase}/{idTrimestre}/{salonId}")
    public ResponseEntity<List<CalificacionesDTO>> obtenerCalificacionesPorTrimestreYClasesDTO(
            @PathVariable("idClase") int idClase,
            @PathVariable("idTrimestre") int idTrimestre,
            @PathVariable("salonId") int salonId) {

        // Obtener la lista de calificaciones existentes para la clase y trimestre
        List<CalificacionesTrimestrales> calificaciones = iCalificacionesTrimestralesService.findCalificacionesTrimandClases(idClase, idTrimestre);

        // Obtener la lista de estudiantes del salón
        List<Estudiante> estudiantes = iEstudianteService.obtenerEstudiantesPorSalon(salonId);

        // Verificar si cada estudiante tiene calificaciones, si no, agregar una nueva calificación
        for (Estudiante estudiante : estudiantes) {
            boolean estudianteTieneCalificacion = calificaciones.stream()
                    .anyMatch(calificacion -> calificacion.getEstudiante().getId() == estudiante.getId());

            // Si no tiene calificación, agregar una nueva calificación con valores predeterminados
            if (!estudianteTieneCalificacion) {
                CalificacionesTrimestrales nuevaCalificacion = new CalificacionesTrimestrales();
                nuevaCalificacion.setEstudiante(estudiante);
                nuevaCalificacion.setNota1(0.0); // Inicializar con 0
                nuevaCalificacion.setNota2(0.0); // Inicializar con 0
                nuevaCalificacion.setNota3(0.0); // Inicializar con 0
                nuevaCalificacion.setPromedio(0.0); // Inicializar con 0
                nuevaCalificacion.setClases(new Clases(idClase));
                nuevaCalificacion.setTrimestre(new Trimestre(idTrimestre)); // Establecer el trimestre
                nuevaCalificacion.setAnio(LocalDate.now().getYear()); // Establecer el año actual

                // Guardar la nueva calificación en la base de datos
                iCalificacionesTrimestralesService.Guardar(nuevaCalificacion);

                // Agregar la nueva calificación a la lista
                calificaciones.add(nuevaCalificacion);
            }
        }

        // Transformar la lista de calificaciones a DTOs
        List<CalificacionesDTO> dtoList = calificaciones.stream()
                .map(calificacion -> new CalificacionesDTO(
                        calificacion.getId(),
                        calificacion.getNota1(),
                        calificacion.getNota2(),
                        calificacion.getNota3(),
                        calificacion.getPromedio(),
                        calificacion.getEstudiante().getNombre() + " " + calificacion.getEstudiante().getApellido() +" "+ calificacion.getEstudiante().getApellidoMaterno(),
                        calificacion.getClases().getId(),
                        calificacion.getTrimestre().getId()))
                .collect(Collectors.toList());

        if (dtoList.isEmpty()) {
            return ResponseEntity.notFound().build(); // Devuelve 404 si no se encuentran calificaciones
        }

        return ResponseEntity.ok(dtoList); // Devuelve la lista de calificaciones con 200 OK
    }

    @PostMapping("/guardarCalificacionesTrimestrales")
    public ResponseEntity<?> guardarCalificacionesTrim(@RequestBody CalificacionesTrimestrales calificaciones) {
        try {
            // Buscar la calificación existente en la base de datos (Optional)
            Optional<CalificacionesTrimestrales> existenteOpt = iCalificacionesTrimestralesService.ConsultarId(calificaciones.getId());

            // Verificar si existe la calificación
            if (!existenteOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Calificación no encontrada");
            }
            // Obtener el objeto CalificacionesTrimestrales del Optional
            CalificacionesTrimestrales existente = existenteOpt.get();
            // Actualizar las notas y promedio
            existente.setNota1(calificaciones.getNota1());
            existente.setNota2(calificaciones.getNota2());
            existente.setNota3(calificaciones.getNota3());
            existente.setPromedio(calificaciones.getPromedio());
            // Guardar la calificación actualizada
            iCalificacionesTrimestralesService.Guardar(existente);  // Llamando al método de tu servicio

            return ResponseEntity.ok("Calificaciones guardadas correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar las calificaciones.");
        }
    }


}

