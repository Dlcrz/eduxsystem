package sistema.colegio.eduxsystem.Repositorios;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sistema.colegio.eduxsystem.Clases.Curso;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface ICurso extends CrudRepository <Curso, Integer> {

    @Query(value = "SELECT * FROM curso "
            + "WHERE nombre LIKE %?1% "
            + "OR descripcion LIKE %?1%", nativeQuery = true)
    List<Curso> findForAll(String desc);
    boolean existsByNombre(String nombre);
    boolean existsById(int id);

}
