package sistema.colegio.eduxsystem.Interfaces;

import sistema.colegio.eduxsystem.Clases.Usuario;
import sistema.colegio.eduxsystem.DTO.UsuarioDTO;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    public List<Usuario> Listar();
    public Optional<Usuario> ConsultarId(int id);
    public void Guardar(Usuario u);
    public void  Eliminar(int id);
    public List<Usuario> Buscar(String desc);
    public boolean existsByDni(String dni);

    public  boolean existsById(int id);

    public Usuario findByEmail(String email);

    Usuario guardar (UsuarioDTO usuarioDto);
}
