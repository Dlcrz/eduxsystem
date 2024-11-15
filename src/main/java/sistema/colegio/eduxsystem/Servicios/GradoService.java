package sistema.colegio.eduxsystem.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema.colegio.eduxsystem.Clases.Grados;
import sistema.colegio.eduxsystem.Interfaces.IGradoService;
import sistema.colegio.eduxsystem.Repositorios.IGrados;

import java.util.List;
import java.util.Optional;

@Service
public class GradoService implements IGradoService {

    @Autowired
    private IGrados data;

    @Override
    public List<Grados> Listar() {
        return (List<Grados>) data.findAll();
    }

    @Override
    public Optional<Grados> ConsultarId(int id) {
        return data.findById(id);
    }

    @Override
    public void Guardar(Grados g) {
        data.save(g);
    }

    @Override
    public void Eliminar(int id) {
        data.deleteById(id);
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return data.existsByNombre(nombre);
    }

    @Override
    public boolean existsById(int id) {

            return data.existsById(id);

    }
}
