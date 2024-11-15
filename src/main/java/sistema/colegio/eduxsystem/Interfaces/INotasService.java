package sistema.colegio.eduxsystem.Interfaces;

import java.util.List;
import java.util.Optional;
import sistema.colegio.eduxsystem.Clases.RegistroNota;

public interface INotasService {
    public List<RegistroNota> Listar();
    public Optional<RegistroNota> ConsultarId(int id);
    public void Guardar(RegistroNota e);
    public void Eliminar(int id);
    public List<Object[]> listarNotasConNombres();

    List<Object[]> listarNotasConNombresYSalon(int salonId);
    List<Object[]> listarNotasConNombresYSalonYCurso(int salonId, int cursoId);

}
