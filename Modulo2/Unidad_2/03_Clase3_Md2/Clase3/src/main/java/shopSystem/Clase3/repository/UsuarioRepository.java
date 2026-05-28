package shopSystem.Clase3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopSystem.Clase3.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);
}
