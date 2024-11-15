package sistema.colegio.eduxsystem.Repositorios;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sistema.colegio.eduxsystem.Clases.Estudiante;

import java.util.List;

@Repository
public interface IEstudiante  extends CrudRepository<Estudiante, Integer> {

    @Query(value = "SELECT * FROM estudiante "
            + "WHERE nombre LIKE %?1% "
            + "OR apellido LIKE %?1% "
            + "OR dni LIKE %?1% "
            + "OR direccion LIKE %?1%"
            + "OR salon LIKE %?1%", nativeQuery = true)
    List<Estudiante> findForAll(String desc);

    boolean existsByDni(String dni);
    boolean existsById(int id);

    // Nuevo
    @Query(value = "SELECT id FROM estudiante ORDER BY id DESC LIMIT 1", nativeQuery = true)
    public int obtenerUltimoID();

    List<Estudiante> findBySalonId(int salonId);

    List<Estudiante> findByGradosId(int gradoId);
    List<Estudiante> findByGradosIdAndSalonIsNull(int gradoId);


    int countBySalonId(int salonId);

}
