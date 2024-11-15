package sistema.colegio.eduxsystem.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema.colegio.eduxsystem.Clases.Asistencia;
import sistema.colegio.eduxsystem.Clases.CalificacionesTrimestrales;
import sistema.colegio.eduxsystem.Clases.Estudiante;
import sistema.colegio.eduxsystem.Interfaces.IAsistenciaService;
import sistema.colegio.eduxsystem.Repositorios.IAsistencia;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AsistenciaService implements IAsistenciaService {

@Autowired
private IAsistencia data;
    @Override
    public List<Asistencia> Listar() {
        return (List<Asistencia>) data.findAll();
    }

    @Override
    public Optional<Asistencia> ConsultarId(int id) {
        return data.findById(id);
    }

    @Override
    public void Guardar(Asistencia a) {
        data.save(a);
    }
    @Override
    public void Eliminar(int id) {
        data.deleteById(id);
    }

    @Override
    public List<Asistencia> Buscar(String desc) {
        return  data.findForAll(desc);
    }


    @Override
    public boolean existsById(int id) {
        return data.existsById(id);
    }
    @Override
    public List<Asistencia> obtenerEstudiantesPorClases(int claseId) {

        return data.findByClasesId(claseId);
    }

    @Override
    public List<Asistencia> findAsistandclaseandate(int idClase, LocalDate fecha) {
        return data.findAsistandclaseandate(idClase, fecha);
    }

}
