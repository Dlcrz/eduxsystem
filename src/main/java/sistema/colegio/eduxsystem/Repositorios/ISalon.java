package sistema.colegio.eduxsystem.Repositorios;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sistema.colegio.eduxsystem.Clases.Salon;

import java.util.List;

@Repository
public interface ISalon extends CrudRepository<Salon, Integer> {

    @Query(value = "SELECT * FROM salon "
        + "WHERE codcorrelativo LIKE %?1% "
        + "OR seccion LIKE %?1% "
        + "OR grado LIKE %?1%", nativeQuery = true)

    List<Salon> findForAll(String desc);

    boolean existsByCodcorrelativo(String codcorrelativo);

    boolean existsById(int id);
}

