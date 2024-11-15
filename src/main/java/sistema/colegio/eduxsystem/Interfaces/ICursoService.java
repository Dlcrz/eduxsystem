package sistema.colegio.eduxsystem.Interfaces;

import sistema.colegio.eduxsystem.Clases.Curso;

import java.util.List;
import java.util.Optional;

public interface ICursoService {

    public List<Curso> Listar();
    public Optional<Curso> ConsultarId(int id);
    public void Guardar(Curso c);
    public void  Eliminar(int id);
    public List<Curso> Buscar(String desc);
    public boolean existsByNombre(String nombre);

    public  boolean existsById(int id);
}
