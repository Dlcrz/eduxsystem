package sistema.colegio.eduxsystem.Repositorios;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sistema.colegio.eduxsystem.Clases.Clases;
import sistema.colegio.eduxsystem.Clases.Salon;

import java.time.Year;
import java.util.List;

@Repository
public interface IClases extends CrudRepository<Clases, Integer> {

    @Query("FROM Clases c WHERE c.salon.id = ?1")
    public List<Clases> findClasesAula(int id);

    @Query("FROM Clases c WHERE c.salon.id = ?1 AND c.anio = ?2")
    public List<Clases> findClasesAulaPorAnio(int id, Year anio);
}
