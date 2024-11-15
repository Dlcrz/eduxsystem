package sistema.colegio.eduxsystem.Interfaces;

import sistema.colegio.eduxsystem.Clases.CalificacionesTrimestrales;
import sistema.colegio.eduxsystem.Clases.Clases;
import sistema.colegio.eduxsystem.Clases.RegistroNota;

import java.util.List;
import java.util.Optional;

public interface ICalificacionesTrimestralesService {

    public List<CalificacionesTrimestrales> Listar();
    public Optional<CalificacionesTrimestrales> ConsultarId(int id);
    public void Guardar(CalificacionesTrimestrales c);
    public void Eliminar(int id);
    public List<CalificacionesTrimestrales> findCalificacionesTrimandClases(int idClase, int idTrimestre);

}
