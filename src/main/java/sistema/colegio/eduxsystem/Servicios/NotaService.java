package sistema.colegio.eduxsystem.Servicios;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema.colegio.eduxsystem.Clases.RegistroNota;
import sistema.colegio.eduxsystem.Interfaces.INotasService;
import sistema.colegio.eduxsystem.Repositorios.INotas;

@Service
public class NotaService implements INotasService{
    @Autowired
    INotas data;

    @Override
    public List<RegistroNota> Listar() {
        return (List<RegistroNota>)data.findAll();
    }

    @Override
    public Optional<RegistroNota> ConsultarId(int id) {
        return data.findById(id);
    }

    @Override
    public void Guardar(RegistroNota notas) {
        data.save(notas);
    }

    @Override
    public void Eliminar(int id) {
        data.deleteById(id);
    }

    @Override
    public List<Object[]> listarNotasConNombres() {
        return data.listarNotasConNombres();
    }

    @Override
    public List<Object[]> listarNotasConNombresYSalon(int salonId) {
        return data.listarNotasConNombresYSalon(salonId);
    }

    @Override
    public List<Object[]> listarNotasConNombresYSalonYCurso(int salonId, int cursoId) {
        return data.listarNotasConNombresYSalonYCurso(salonId, cursoId);
    }
}
