package sistema.colegio.eduxsystem.Servicios;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sistema.colegio.eduxsystem.Clases.Usuario;
import sistema.colegio.eduxsystem.DTO.UsuarioDTO;
import sistema.colegio.eduxsystem.Interfaces.IUsuarioService;
import sistema.colegio.eduxsystem.Repositorios.IUsuario;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private IUsuario data;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> Listar() {
        return (List<Usuario>) data.findAll();
    }

    @Override
    public Optional<Usuario> ConsultarId(int id) {
        return data.findById(id);
    }

    @Override
    public void Guardar(Usuario u) {
        data.save(u);
    }

    @Override
    public void Eliminar(int id) {
        data.deleteById(id);
    }

    @Override
    public List<Usuario> Buscar(String desc) {
        return  data.findForAll(desc);
    }

    @Override
    public boolean existsByDni(String dni) {
        return data.existsByDni(dni);
    }

    @Override
    public boolean existsById(int id) {
        return data.existsById(id);
    }

    @Override
    public Usuario findByEmail(String email) {
        return data.findByEmail(email);
    }

    @Override
    public Usuario guardar(UsuarioDTO usuarioDto) {
        Usuario usuario = new Usuario(usuarioDto.getNombre(), usuarioDto.getApellido(), usuarioDto.getRol(), usuarioDto.getDni(), usuarioDto.getCelular(), usuarioDto.getEmail(), usuarioDto.getDireccion(), usuarioDto.getUser(), passwordEncoder.encode(usuarioDto.getPassword()));
        return data.save(usuario);
    }
}
