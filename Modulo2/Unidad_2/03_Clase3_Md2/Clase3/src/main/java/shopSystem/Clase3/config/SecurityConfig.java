package shopSystem.Clase3.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// @Configuration: esta clase contiene beans que Spring debe registrar.
// @EnableWebSecurity: activa el módulo de seguridad web de Spring Security.
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // BEAN 1: SecurityFilterChain — reglas de acceso a la API.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // CSRF desactivado: no aplica en APIs REST con tokens.
                // Con CSRF activo, todos los POST/PUT/DELETE responden 403.
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // Swagger público: acceso sin autenticación en desarrollo.
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN", "CLIENTE")
                        .requestMatchers(HttpMethod.POST, "/api/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasAnyRole("ADMIN")

                        // Todo lo demás requiere autenticación.
                        .anyRequest().authenticated()
                )

                // HTTP Basic: usuario y contraseña en el encabezado Authorization.
                .httpBasic(basic -> basic.realmName("ShopSystem API"));

        return http.build();
    }

    // BEAN 2: PasswordEncoder — define cómo se hashean las contraseñas.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // BEAN 3: UserDetailsService — define de dónde carga Spring los usuarios.
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//
//        // Usuario ADMIN: acceso total al sistema.
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(encoder.encode("admin123")) // contraseña hasheada con BCrypt
//                .roles("ADMIN") // Spring agrega el prefijo ROLE_ internamente
//                .build();
//
//        // Usuario CLIENTE: acceso limitado (lectura).
//        UserDetails cliente = User.builder()
//                .username("cliente")
//                .password(encoder.encode("cliente123"))
//                .roles("CLIENTE")
//                .build();
//
//        // InMemoryUserDetailsManager mantiene estos usuarios en memoria.
//        return new InMemoryUserDetailsManager(admin, cliente);
//    }
}