package sistema.colegio.eduxsystem.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AccesoPersonalizado implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // Método de llamado cuando la autenticación tiene éxito.

        var autoridades = authentication.getAuthorities();
        var roles = autoridades.stream().map(r -> r.getAuthority()).findFirst();

        if (roles.orElse("").equals("Administrador") || roles.orElse("").equals("Profesor")|| roles.orElse("").equals("Director") ) {
            response.sendRedirect("/inicio");
        } else {
            response.sendRedirect("/error");
        }
    }
}