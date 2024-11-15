package sistema.colegio.eduxsystem.Interfaces;

import sistema.colegio.eduxsystem.Clases.Estudiante;

import java.util.List;
import java.util.Optional;

public interface IEstudianteService {
    public List<Estudiante> Listar();
    public Optional<Estudiante> ConsultarId(int id);
    public void Guardar(Estudiante e);
    public void  Eliminar(int id);
    public List<Estudiante> Buscar(String desc);
    public boolean existsByDni(String dni);

    public  boolean existsById(int id);

    public int obtenerUltimoID();

    List<Estudiante> obtenerEstudiantesPorSalon(int salonId);
    int contarEstudiantesEnAula(int aulaId); // Método agregado

    List<Estudiante> obtenerEstudiantePorGradoID(int gradoId);

    List<Estudiante> ObtenerEstudiantesPorGradoSalonIsNull(int gradoId);
}
