package shopSystem.Clase3.security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro JWT — se ejecuta UNA VEZ por cada request HTTP.
 *
 * Responsabilidades:
 *  1. Extraer el token del header "Authorization: Bearer <token>"
 *  2. Validar el token usando JwtUtil
 *  3. Cargar el usuario desde BD vía UserDetailsService
 *  4. Registrar la autenticación en el SecurityContextHolder
 *
 * Extiende OncePerRequestFilter para garantizar ejecución única por request,
 * incluso en cadenas de filtros complejas.
 *
 * Se registra en SecurityConfig ANTES del UsernamePasswordAuthenticationFilter.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    // Inyección por constructor — práctica recomendada con Spring Security
    public JwtAuthFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // --- Paso 1: Extraer el header Authorization ---
        final String authHeader = request.getHeader("Authorization");

        // Si no hay header o no empieza con "Bearer ", pasar al siguiente filtro.
        // Esto cubre rutas públicas como POST /auth/login.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraer el token eliminando el prefijo "Bearer " (7 caracteres)
        final String jwt = authHeader.substring(7);

        try {
            // --- Paso 2: Extraer el username del token ---
            // extractUsername() también valida la firma internamente (via jjwt).
            // Si el token está malformado o la firma es inválida, lanza JwtException.
            final String username = jwtUtil.extraerUsername(jwt);

            // Solo procesar si hay username Y el SecurityContext está vacío.
            // Si ya hay autenticación en el contexto, no pisar lo que ya existe.
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // --- Paso 3: Cargar el usuario desde BD ---
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // --- Paso 4: Validar token contra el usuario de BD ---
                // isTokenValid verifica: username coincide + token no expirado
                if (jwtUtil.esTokenValido(jwt, userDetails)) {

                    // Construir el objeto de autenticación de Spring Security.
                    // Tercer parámetro: authorities/roles del usuario.
                    // Segundo parámetro (credentials): null porque ya no necesitamos password.
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    // Adjuntar detalles del request (IP, session) al token de autenticación.
                    // Útil para auditoría y para algunos mecanismos internos de Spring Security.
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    // Registrar la autenticación en el contexto de seguridad.
                    // A partir de este punto, el request está autenticado para Spring Security.
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

        } catch (io.jsonwebtoken.JwtException e) {
            // Token inválido, malformado o expirado.
            // No registramos autenticación → Spring Security rechazará el request (401).
            // No relanzamos la excepción para no romper la cadena de filtros.
            // El logger puede ser reemplazado por el framework de logging de tu proyecto.
            logger.warn("JWT inválido: " + e.getMessage());
        }

        // Continuar con el siguiente filtro de la cadena, siempre.
        filterChain.doFilter(request, response);
    }
}