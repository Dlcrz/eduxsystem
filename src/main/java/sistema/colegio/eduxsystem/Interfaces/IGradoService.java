package sistema.colegio.eduxsystem.Interfaces;

import sistema.colegio.eduxsystem.Clases.Curso;
import sistema.colegio.eduxsystem.Clases.Grados;

import java.util.List;
import java.util.Optional;

public interface IGradoService {

    public List<Grados> Listar();
    public Optional<Grados> ConsultarId(int id);
    public void Guardar(Grados g);
    public void  Eliminar(int id);
    public boolean existsByNombre(String nombre);

    boolean existsById(int id);
}
