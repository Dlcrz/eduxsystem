package sistema.colegio.eduxsystem.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sistema.colegio.eduxsystem.Clases.Usuario;

import java.util.Collection;
import java.util.List;

public class DetallesUsuario implements UserDetails {

    private Usuario usuario;
    public DetallesUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(() -> usuario.getRol());
    }
    public Integer getId() {
        return usuario.getId();
    }
    public String getNombre() {
        return usuario.getNombre();
    }

    public String getApellido() {
        return usuario.getApellido();
    }
    public String getRol() {
        return usuario.getRol();
    }
    public String getDni() {
        return usuario.getDni();
    }
    public String getCelular() {
        return usuario.getCelular();
    }

    public String getDireccion() {
        return usuario.getDireccion();
    }

    public String getUser() {
        return usuario.getUser();
    }
    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
