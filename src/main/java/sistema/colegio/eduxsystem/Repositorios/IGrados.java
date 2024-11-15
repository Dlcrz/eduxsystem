package sistema.colegio.eduxsystem.Repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sistema.colegio.eduxsystem.Clases.Curso;
import sistema.colegio.eduxsystem.Clases.Grados;

@Repository
public interface IGrados extends CrudRepository<Grados, Integer> {
    boolean existsByNombre(String nombre);
}
