package sistema.colegio.eduxsystem.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.colegio.eduxsystem.Clases.*;
import sistema.colegio.eduxsystem.DTO.AsistenciaDTO;
import sistema.colegio.eduxsystem.DTO.CalificacionesDTO;
import sistema.colegio.eduxsystem.Interfaces.*;
import sistema.colegio.eduxsystem.util.ReportGeneratorService;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api_colegio")
public class ControladorAPIs {

    @Autowired
    IEstudianteService iEstudianteService;

    @Autowired
    ISalonService iSalonService;

    @Autowired
    INotasService notasService;

    @Autowired
    ICursoService iCursoService;

    @Autowired
    IGradoService iGradoService;

    @Autowired
    IAsistenciaService iAsistenciaService;

    @Autowired
    IClasesService iClasesService;

    @Autowired
    ReportGeneratorService reportService;

    @PostMapping("/agregarEstudiante")
    public ResponseEntity<?> agregarEstudiante(@RequestParam int estudianteId, @RequestParam int aulaId) {
        try {
            // Obtener el estudiante por ID
            Optional<Estudiante> optionalEstudiante = iEstudianteService.ConsultarId(estudianteId);
            if (!optionalEstudiante.isPresent()) {
                return ResponseEntity.status(404).body("Estudiante no encontrado.");
            }

            // Obtener el aula por ID
            Optional<Salon> optionalAula = iSalonService.ConsultarId(aulaId);
            if (!optionalAula.isPresent()) {
                return ResponseEntity.status(404).body("Aula no encontrada.");
            }

            // Lógica de conteo y verificación
            int cantidadEstudiantesEnAula = iEstudianteService.contarEstudiantesEnAula(aulaId);
            if (cantidadEstudiantesEnAula < optionalAula.get().getCapacidad()) {
                Estudiante estudiante = optionalEstudiante.get();
                estudiante.setSalon(optionalAula.get());
                iEstudianteService.Guardar(estudiante);
                return ResponseEntity.ok().body("Estudiante agregado correctamente.");
            } else {
                return ResponseEntity.status(400).body("Capacidad máxima alcanzada: " + optionalAula.get().getCapacidad());
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * METODOS REPORTES localhost:90/api_colegio/report
     */
    @GetMapping("/reporteCalificaciones")
    public ResponseEntity<byte[]> generarReporte() {
        try {
            byte[] report = reportService.generarReport("Report_initial");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.add("Content-Disposition", "inline; filename=reporte.pdf");

            return new ResponseEntity<>(report, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * METODOS ASISTENCIAS
     */

    @GetMapping("/asistencias/{claseId}/{salonId}/{fecha_asistencia}")
    public ResponseEntity<List<AsistenciaDTO>> obtenerAsistenciaClaseDTO(
            @PathVariable("claseId") int idClase,
            @PathVariable("salonId") int salonId,
            @PathVariable("fecha_asistencia") LocalDate fecha_asistencia) {

        List<Asistencia> asistencias = iAsistenciaService.findAsistandclaseandate(idClase, fecha_asistencia);
        // Obtener la lista de asistencias existentes
        // Obtener la lista de estudiantes del salón
        List<Estudiante> estudiantes = iEstudianteService.obtenerEstudiantesPorSalon(salonId);

        // Verificar si cada estudiante tiene asistencia, si no, agregar una nueva
        for (Estudiante estudiante : estudiantes) {
            boolean estudianteTieneAsistencia = asistencias.stream()
                    .anyMatch(asistencia -> asistencia.getEstudiante().getId() == estudiante.getId());

            // Si no tiene asistencia, agregar una nueva con valores predeterminados
            if (!estudianteTieneAsistencia) {

                Asistencia nuevaAsistencia = new Asistencia();
                nuevaAsistencia.setEstudiante(estudiante);
                nuevaAsistencia.setFecha(fecha_asistencia);
                nuevaAsistencia.setClases(new Clases(idClase));
                nuevaAsistencia.setAsistencia("NA");
                // Guardar la nueva asistencia en la base de datos
                iAsistenciaService.Guardar(nuevaAsistencia);

                asistencias.add(nuevaAsistencia);
            }
        }

        // Transformar la lista de asistencias a DTOs
        List<AsistenciaDTO> dtoList = asistencias.stream()
                .map(asistencia -> new AsistenciaDTO(
                        asistencia.getId(),
                        asistencia.getFecha(),
                        asistencia.getAsistencia(),
                        asistencia.getEstudiante().getNombre() + " " + asistencia.getEstudiante().getApellido() +" "+ asistencia.getEstudiante().getApellidoMaterno(),
                        asistencia.getClases().getId()))
                .collect(Collectors.toList());

        if (dtoList.isEmpty()) {
            return ResponseEntity.notFound().build(); // Devuelve 404 si no se encuentran calificaciones
        }

        return ResponseEntity.ok(dtoList); // Devuelve la lista de calificaciones con 200 OK
    }


    @PostMapping("/guardarAsistenciasClase")
    public ResponseEntity<?> guardarAsistenciasClase(@RequestBody Asistencia asistencias) {
        try {
            Optional<Asistencia> existenteOpt = iAsistenciaService.ConsultarId(asistencias.getId());
            if (!existenteOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Asistencia no encontrada.");
            }
            Asistencia existente = existenteOpt.get();
            existente.setAsistencia(asistencias.getAsistencia());
            iAsistenciaService.Guardar(existente);
            return ResponseEntity.ok("Asistencia guardadas correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la asistencia.");
        }
    }

    /**
     * METODOS REPORTES ASISTENCIAS
     */
    //localhost:90/api_colegio/reportes/asistencias/2/4/2024-11-14
  /*  @GetMapping("/reportes/asistencias/{claseId}/{salonId}/{fecha_asistencia}")
    public ResponseEntity<List<AsistenciaDTO>> ReporteAsistenciasDTO(
            @PathVariable("claseId") int idClase,
            @PathVariable("salonId") int salonId,
            @PathVariable("fecha_asistencia") LocalDate fecha_asistencia) {

        // Obtiene solo las asistencias existentes
        List<Asistencia> asistencias = iAsistenciaService.findAsistandclaseandate(idClase, fecha_asistencia);

        // Transforma la lista de asistencias a DTOs sin agregar nuevas asistencias
        List<AsistenciaDTO> dtoList = asistencias.stream()
                .map(asistencia -> new AsistenciaDTO(
                        asistencia.getId(),
                        asistencia.getFecha(),
                        asistencia.getAsistencia(),
                        asistencia.getEstudiante().getNombre() + " " + asistencia.getEstudiante().getApellido() +" "+ asistencia.getEstudiante().getApellidoMaterno(),
                        asistencia.getClases().getId()))
                .collect(Collectors.toList());

        if (dtoList.isEmpty()) {
            return ResponseEntity.notFound().build(); // Devuelve 404 si no se encuentran asistencias
        }

        return ResponseEntity.ok(dtoList); // Devuelve la lista de asistencias existentes con 200 OK
    }
*/


    @GetMapping("/reportes/asistencias/{claseId}/{salonId}/{fecha_asistencia}")
    public ResponseEntity<?> ReporteAsistenciasDTO(
            @PathVariable("claseId") int idClase,
            @PathVariable("salonId") int salonId,
            @PathVariable("fecha_asistencia") LocalDate fecha_asistencia) {

        // Obtener la clase con el idClase para verificar el salonId
        Optional<Clases> clase = iClasesService.ConsultarId(idClase);

        // Verificar si la clase no existe
        if (clase.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("La clase con el ID proporcionado no existe.");
        }

        // Verificar si el salonId de la clase coincide con el salonId proporcionado
        if (clase.get().getSalon().getId() != salonId) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se ha encontrado la clase para el aula especificada.");
        }

        // Obtiene solo las asistencias existentes
        List<Asistencia> asistencias = iAsistenciaService.findAsistandclaseandate(idClase, fecha_asistencia);

        // Transforma la lista de asistencias a DTOs sin agregar nuevas asistencias
        List<AsistenciaDTO> dtoList = asistencias.stream()
                .map(asistencia -> new AsistenciaDTO(
                        asistencia.getId(),
                        asistencia.getFecha(),
                        asistencia.getAsistencia(),
                        asistencia.getEstudiante().getNombre() + " " + asistencia.getEstudiante().getApellido() +" "+ asistencia.getEstudiante().getApellidoMaterno(),
                        asistencia.getClases().getId()))
                .collect(Collectors.toList());

        if (dtoList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontraron asistencias para la clase y fecha especificadas.");
        }

        return ResponseEntity.ok(dtoList); // Devuelve la lista de asistencias existentes con 200 OK
    }





}
