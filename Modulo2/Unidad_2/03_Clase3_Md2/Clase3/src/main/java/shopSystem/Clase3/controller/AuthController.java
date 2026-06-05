package shopSystem.Clase3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import shopSystem.Clase3.dto.AuthRequest;
import shopSystem.Clase3.dto.AuthResponse;
import shopSystem.Clase3.model.Rol;
import shopSystem.Clase3.model.Usuario;
import shopSystem.Clase3.repository.UsuarioRepository;
import shopSystem.Clase3.security.JwtUtil;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/registro")
    public ResponseEntity<String> registro(@RequestBody AuthRequest request) {
        // Verificamos si el username ya existe para evitar duplicados
        if (usuarioRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("El usuario ya existe");
        }

        // Creamos el nuevo usuario con el password encriptado
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(request.getUsername());
        nuevoUsuario.setPassword(passwordEncoder.encode(request.getPassword()));
        nuevoUsuario.setRol(Rol.CLIENTE); // por defecto, los nuevos usuarios son USER

        usuarioRepository.save(nuevoUsuario);

        return ResponseEntity.ok("Usuario registrado correctamente");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        // authenticate() verifica las credenciales usando nuestro DaoAuthenticationProvider
        // Si las credenciales son incorrectas, lanza BadCredentialsException → 401
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Si llegamos aquí, la autenticación fue exitosa
        // Extraemos el UserDetails del objeto de autenticación
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Generamos el JWT y lo devolvemos
        String rol = userDetails.getAuthorities()
                .stream()
                .findFirst()
                .map(a -> a.getAuthority())
                .orElse("CLIENTE");

        String token = jwtUtil.generateToken(userDetails, rol);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
