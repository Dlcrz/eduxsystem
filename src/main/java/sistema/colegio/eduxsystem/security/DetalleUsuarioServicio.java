package sistema.colegio.eduxsystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sistema.colegio.eduxsystem.Clases.Usuario;
import sistema.colegio.eduxsystem.Repositorios.IUsuario;

@Service
public class DetalleUsuarioServicio implements UserDetailsService {

    @Autowired
    private IUsuario iUsuario;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = iUsuario.findByEmail(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        return new DetallesUsuario(usuario);

    }

}