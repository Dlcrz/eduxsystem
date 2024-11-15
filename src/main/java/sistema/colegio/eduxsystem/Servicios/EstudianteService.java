package sistema.colegio.eduxsystem.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema.colegio.eduxsystem.Clases.Estudiante;

import sistema.colegio.eduxsystem.Interfaces.IEstudianteService;
import sistema.colegio.eduxsystem.Repositorios.IEstudiante;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService implements IEstudianteService {

    @Autowired
    IEstudiante data;

    @Override
    public List<Estudiante> Listar() {
        return (List<Estudiante>) data.findAll();
    }

    @Override
    public Optional<Estudiante> ConsultarId(int id) {
        return data.findById(id);
    }

    @Override
    public void Guardar(Estudiante e) {
        data.save(e);
    }

    @Override
    public void Eliminar(int id) {
        data.deleteById(id);
    }

    @Override
    public List<Estudiante> Buscar(String desc) {
        return  data.findForAll(desc);
    }

    @Override
    public boolean existsByDni(String dni) {
        return data.existsByDni(dni);
    }

    @Override
    public boolean existsById(int id) {
        return data.existsById(id);
    }

    // Nuevos
    @Override
    public int obtenerUltimoID() {
        return data.obtenerUltimoID();
    }

    @Override
    public List<Estudiante> obtenerEstudiantesPorSalon(int salonId) {

        return data.findBySalonId(salonId);
    }

    @Override
    public int contarEstudiantesEnAula(int aulaId) {
        return data.countBySalonId(aulaId);
    }

    @Override
    public List<Estudiante> obtenerEstudiantePorGradoID(int gradoId) {
        return data.findByGradosId(gradoId);
    }

    @Override
    public List<Estudiante> ObtenerEstudiantesPorGradoSalonIsNull(int gradoId) {

        return data.findByGradosIdAndSalonIsNull(gradoId);
    }

}




