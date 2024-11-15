package sistema.colegio.eduxsystem.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.colegio.eduxsystem.Clases.Clases;
import sistema.colegio.eduxsystem.Clases.Curso;
import sistema.colegio.eduxsystem.Clases.Salon;
import sistema.colegio.eduxsystem.Clases.Usuario;
import sistema.colegio.eduxsystem.Interfaces.IClasesService;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/api/clases")
public class ControladorClases {

    @Autowired
    IClasesService iClasesService;

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarClase(@PathVariable int id) {
        iClasesService.Eliminar(id);  // Método en tu servicio para eliminar la clase
        return ResponseEntity.noContent().build();  // Retorna 204 si es exitoso
    }

    @GetMapping("/salon/{idSalon}")
    public ResponseEntity<List<Clases>> obtenerClasesPorSalonYAnio(
            @PathVariable("idSalon") int idSalon,
            @RequestParam("anio") int anio) {

        // Convertir el año a tipo Year
        Year year = Year.of(anio);

        // Llamar al servicio para obtener las clases filtradas
        List<Clases> clases = iClasesService.findClasesAulaPorAnio(idSalon,year);

        // Si no se encuentran clases, devolver un 404 Not Found
        if (clases.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Devolver la lista de clases con un 200 OK
        return ResponseEntity.ok(clases);
    }

    // Método para guardar una nueva clase
    @PostMapping("/guardar")
    public ResponseEntity<Clases> guardarClase(
            @RequestParam("profesorId") int profesorId,
            @RequestParam("cursoId") int cursoId,
            @RequestParam("salonId") int salonId) {

        // Crea una nueva instancia de Clases
        Clases c = new Clases();

        // Aquí deberías establecer las relaciones adecuadas, puedes obtener los objetos completos si los necesitas
        Usuario usuario = new Usuario();
        usuario.setId(profesorId); // Asumiendo que ya tienes el id del usuario (profesor)

        Curso curso = new Curso();
        curso.setId(cursoId); // Asumiendo que ya tienes el id del curso

        Salon salon = new Salon();
        salon.setId(salonId); // Asumiendo que ya tienes el id del salón
// Convertir el año a tipo Year
        int anio = LocalDate.now().getYear();
        Year year = Year.of(anio);
        // Asignar valores a la nueva clase
        c.setAnio(year);
        c.setUsuario(usuario);
        c.setCurso(curso);
        c.setSalon(salon);
        try {
            iClasesService.Guardar(c);
            return ResponseEntity.status(HttpStatus.CREATED).body(c);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // Retorna 500 si hay un error
        }

    }
}
