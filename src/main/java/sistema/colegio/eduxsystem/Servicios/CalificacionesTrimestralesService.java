package sistema.colegio.eduxsystem.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema.colegio.eduxsystem.Clases.CalificacionesTrimestrales;
import sistema.colegio.eduxsystem.Clases.Clases;
import sistema.colegio.eduxsystem.Clases.RegistroNota;
import sistema.colegio.eduxsystem.Interfaces.ICalificacionesTrimestralesService;
import sistema.colegio.eduxsystem.Repositorios.ICalificacionesTrimestrales;

import java.util.List;
import java.util.Optional;

@Service
public class CalificacionesTrimestralesService implements ICalificacionesTrimestralesService {

    @Autowired
    ICalificacionesTrimestrales data;
    @Override
    public List<CalificacionesTrimestrales> Listar() {
        return (List<CalificacionesTrimestrales>)data.findAll();
    }

    @Override
    public Optional<CalificacionesTrimestrales> ConsultarId(int id) {
        return data.findById(id);
    }

    @Override
    public void Guardar(CalificacionesTrimestrales c) {
        data.save(c);
    }

    @Override
    public void Eliminar(int id) {
        data.deleteById(id);
    }

    @Override
    public List<CalificacionesTrimestrales> findCalificacionesTrimandClases(int idClase, int idTrimestre) {
        return data.findCalificacionesTrimandClases(idClase, idTrimestre);
    }
}
