package sistema.colegio.eduxsystem.Repositorios;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sistema.colegio.eduxsystem.Clases.Asistencia;
import sistema.colegio.eduxsystem.Clases.CalificacionesTrimestrales;
import sistema.colegio.eduxsystem.Clases.Estudiante;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface IAsistencia extends CrudRepository<Asistencia, Integer> {

    @Query(value = "SELECT * FROM asistencia "
            + "WHERE id LIKE %?1% "
            + "OR asistencia LIKE %?1%", nativeQuery = true)
    List<Asistencia> findForAll(String desc);

    boolean existsById(int id);
    List<Asistencia> findByClasesId(int claseId);

    @Query("FROM Asistencia a WHERE a.clases.id = ?1 AND a.fecha = ?2")
    public List<Asistencia> findAsistandclaseandate(int idClase, LocalDate fecha);
}