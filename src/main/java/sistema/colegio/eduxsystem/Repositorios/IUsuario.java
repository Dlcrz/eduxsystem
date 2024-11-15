package sistema.colegio.eduxsystem.Repositorios;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sistema.colegio.eduxsystem.Clases.Usuario;

import java.util.List;

@Repository
public interface IUsuario extends CrudRepository<Usuario, Integer> {

    @Query(value = "SELECT * FROM usuario "
            + "WHERE nombre LIKE %?1% "
            + "OR apellido LIKE %?1% "
            + "OR dni LIKE %?1% "
            + "OR celular LIKE %?1% "
            + "OR email LIKE %?1% "
            + "OR user LIKE %?1% "
            + "OR direccion LIKE %?1%", nativeQuery = true)
    List<Usuario> findForAll(String desc);
    boolean existsByDni(String dni);
    boolean existsById(int id);

     Usuario findByEmail (String email);
}
