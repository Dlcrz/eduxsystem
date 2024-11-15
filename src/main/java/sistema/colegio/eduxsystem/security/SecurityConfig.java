package sistema.colegio.eduxsystem.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Autowired
    AccesoPersonalizado accesoPersonalizado;

    @Autowired
    DetalleUsuarioServicio detalleUsuarioServicio;


    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable())
                .authorizeHttpRequests(request -> request.requestMatchers(
                                "/inicio",
                                "/usuario/**",
                                "/curso/**",
                                "/salon/**",
                                "/grado/**",
                                "/notificaciones/**",
                                "/estudiante/**")
                        .hasAnyAuthority("Administrador", "Profesor", "Director")
                        .requestMatchers(
                                "/Plugins/**", "/css/**",
                                "/js/**", "/imagenes/**",
                                "/*/**")
                        .permitAll().anyRequest())
                .formLogin(form -> form.loginPage("/login").loginProcessingUrl("/login")
                        .successHandler(accesoPersonalizado).permitAll())
                .logout(form -> form.invalidateHttpSession(true).clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout").permitAll())
                .headers(headers -> headers.frameOptions().sameOrigin());;

       /* http.exceptionHandling(config -> {
            config.accessDeniedHandler(new AccessDeniedHandler() {
                @Override
                public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
                    response.sendRedirect("/error?message=No existe esta URL.");
                }
            });
        });*/

        return http.build();
    }


    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detalleUsuarioServicio).passwordEncoder(passwordEncoder());
    }
}
