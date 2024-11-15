package sistema.colegio.eduxsystem.Repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sistema.colegio.eduxsystem.Clases.RegistroNota;


@Repository
public interface INotas extends CrudRepository<RegistroNota, Integer> {
    @Query(
        value = "SELECT registronota.*, estudiante.nombre, estudiante.apellido "
                + "FROM registronota "
                + "INNER JOIN estudiante ON registronota.estudiante_id = estudiante.id;",
        nativeQuery = true
    )
    List<Object[]> listarNotasConNombres();

    @Query(
            value = "SELECT registronota.*, estudiante.nombre, estudiante.apellido, salon.codcorrelativo "
                    + "FROM registronota "
                    + "INNER JOIN estudiante ON registronota.estudiante_id = estudiante.id "
                    + "INNER JOIN salon ON estudiante.salon_id = salon.id "
                    + "WHERE estudiante.salon_id = :salonId",
            nativeQuery = true
    )
    List<Object[]> listarNotasConNombresYSalon(@Param("salonId") int salonId);
   @Query(
           value = "SELECT registronota.*, estudiante.nombre, estudiante.apellido, salon.codcorrelativo "
                   + "FROM registronota "
                   + "INNER JOIN estudiante ON registronota.estudiante_id = estudiante.id "
                   + "INNER JOIN salon ON estudiante.salon_id = salon.id "
                   + "WHERE estudiante.salon_id = :salonId AND registronota.curso_id = :cursoId",
           nativeQuery = true
   )
   List<Object[]> listarNotasConNombresYSalonYCurso(
           @Param("salonId") int salonId,
           @Param("cursoId") int cursoId
   );

}

