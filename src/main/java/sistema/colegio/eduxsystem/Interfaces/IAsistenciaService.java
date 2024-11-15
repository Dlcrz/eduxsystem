package sistema.colegio.eduxsystem.Interfaces;

import sistema.colegio.eduxsystem.Clases.Asistencia;
import sistema.colegio.eduxsystem.Clases.CalificacionesTrimestrales;
import sistema.colegio.eduxsystem.Clases.Estudiante;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IAsistenciaService {
    public List<Asistencia> Listar();
    public Optional<Asistencia> ConsultarId(int id);
    public void Guardar(Asistencia a);
    public void  Eliminar(int id);
    public List<Asistencia> Buscar(String desc);
    public  boolean existsById(int id);

    public List<Asistencia> obtenerEstudiantesPorClases(int claseId);

    public List<Asistencia> findAsistandclaseandate(int idClase, LocalDate fecha);
}
