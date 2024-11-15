package sistema.colegio.eduxsystem.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema.colegio.eduxsystem.Clases.Curso;
import sistema.colegio.eduxsystem.Interfaces.ICursoService;
import sistema.colegio.eduxsystem.Repositorios.ICurso;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService  implements ICursoService {


    @Autowired
   private ICurso data;


    @Override
    public List<Curso> Listar() {
        return (List<Curso>) data.findAll();
    }

    @Override
    public Optional<Curso> ConsultarId(int id) {
        return data.findById(id);
    }

    @Override
    public void Guardar(Curso c) {
        data.save(c);
    }
    @Override
    public void Eliminar(int id) {
        data.deleteById(id);
    }
    @Override
    public List<Curso> Buscar(String desc) {
        return  data.findForAll(desc);
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

