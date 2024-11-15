package sistema.colegio.eduxsystem.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema.colegio.eduxsystem.Clases.Trimestre;
import sistema.colegio.eduxsystem.Interfaces.ITrimestreService;
import sistema.colegio.eduxsystem.Repositorios.ITrimestre;

import java.util.List;
import java.util.Optional;

@Service
public class TrimestreService implements ITrimestreService {

    @Autowired
    ITrimestre data;
    @Override
    public List<Trimestre> Listar() {
        return (List<Trimestre>) data.findAll();
    }

    @Override
    public Optional<Trimestre> ConsultarId(int id) {
        return data.findById(id);
    }

    @Override
    public void Guardar(Trimestre t) {
        data.save(t);
    }

    @Override
    public void Eliminar(int id) {
        data.deleteById(id);
    }

    @Override
    public boolean existsByTrimestreAndAnio(String trimestre, int anio) {
        return data.existsByTrimestreAndAnio(trimestre, anio);
    }

    @Override
    public boolean existsById(int id) {
        return data.existsById(id);
    }

    @Override
    public List<Trimestre> findByAnio(int anio) {
        return data.findByAnio(anio);
    }
}
