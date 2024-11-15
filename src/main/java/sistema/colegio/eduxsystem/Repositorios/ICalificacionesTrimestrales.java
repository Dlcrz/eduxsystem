package sistema.colegio.eduxsystem.Repositorios;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sistema.colegio.eduxsystem.Clases.CalificacionesTrimestrales;
import sistema.colegio.eduxsystem.Clases.Clases;
import sistema.colegio.eduxsystem.Clases.RegistroNota;

import java.time.Year;
import java.util.List;

@Repository
public interface ICalificacionesTrimestrales extends CrudRepository<CalificacionesTrimestrales, Integer> {

    @Query("FROM CalificacionesTrimestrales c WHERE c.clases.id = ?1 AND c.trimestre.id = ?2")
    public List<CalificacionesTrimestrales> findCalificacionesTrimandClases(int idClase, int idTrimestre);

}

