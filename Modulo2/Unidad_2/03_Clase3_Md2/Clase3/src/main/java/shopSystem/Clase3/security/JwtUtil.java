package shopSystem.Clase3.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserDetails userDetails, String rol) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("rol", rol);

        return buildToken(extraClaims, userDetails);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .claims(extraClaims)                          // claims personalizados (rol)
                .subject(userDetails.getUsername())           // claim "sub"
                .issuedAt(new Date(now))                      // claim "iat"
                .expiration(new Date(now + expiration))     // claim "exp"
                .signWith(getSigningKey())                    // firma HMAC-SHA256
                .compact();                                   // serializa a String
    }

    private Claims extraerClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())   // verifica que la firma sea válida
                .build()
                .parseSignedClaims(token)
                .getPayload();                 // devuelve los datos del payload
    }

    /**
     * Extrae el username (subject) del token.
     * Usamos este método en el filtro para saber qué usuario está haciendo la petición.
     */
    public String extraerUsername(String token) {
        return extraerClaims(token).getSubject();
    }

    /**
     * Verifica si el token es válido para el usuario dado.
     * Un token es válido si:
     * 1. El username del token coincide con el usuario que tenemos en base de datos
     * 2. El token no ha expirado
     */
    public boolean esTokenValido(String token, UserDetails userDetails) {
        String username = extraerUsername(token);
        return username.equals(userDetails.getUsername()) && !estaExpirado(token);
    }

    /**
     * Verifica si el token ya expiró comparando su fecha de expiración con ahora.
     */
    private boolean estaExpirado(String token) {
        return extraerClaims(token).getExpiration().before(new Date());
    }
}
