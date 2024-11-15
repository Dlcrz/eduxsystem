package sistema.colegio.eduxsystem.Repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sistema.colegio.eduxsystem.Clases.Curso;
import sistema.colegio.eduxsystem.Clases.Trimestre;

import java.util.List;

@Repository
public interface ITrimestre  extends CrudRepository<Trimestre, Integer>{
    boolean existsByTrimestreAndAnio(String trimestre, int anio);

    List<Trimestre> findByAnio(int anio);

}
