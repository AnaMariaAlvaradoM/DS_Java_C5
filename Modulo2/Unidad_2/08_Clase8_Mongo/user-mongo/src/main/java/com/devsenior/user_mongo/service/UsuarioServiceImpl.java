package com.devsenior.user_mongo.service;
import com.devsenior.user_mongo.DTO.UsuarioRequestDTO;
import com.devsenior.user_mongo.DTO.UsuarioResponseDTO;
import com.devsenior.user_mongo.exception.EmailDuplicadoException;
import com.devsenior.user_mongo.exception.UsuarioNoEncontradoException;
import com.devsenior.user_mongo.model.Usuario;
import com.devsenior.user_mongo.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioServiceImpl implements IUsuarioService {

    private final IUsuarioRepository usuarioRepository;
@Override
    public UsuarioResponseDTO registrar(UsuarioRequestDTO dto) {
        log.info("Intentando registrar usuario con email: {}", dto.getEmail());

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            log.warn("Email duplicado detectado: {}", dto.getEmail());
            throw new EmailDuplicadoException(
                "Ya existe un usuario registrado con el email: " + dto.getEmail()
            );
        }

        Usuario nuevoUsuario = Usuario.builder()
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .activo(true) // siempre activo al registrarse
                .build();

        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);

        log.info("Usuario registrado exitosamente con id: {}", usuarioGuardado.getId());

        // Paso 5: Convertir Entidad → ResponseDTO y retornar
        return mapearAResponseDTO(usuarioGuardado);
    }

    @Override
    public List<UsuarioResponseDTO> consultarTodos() {
        log.info("Consultando todos los usuarios");

        return usuarioRepository.findAll()
                .stream()
                // Por cada Usuario en la lista, lo convertimos a UsuarioResponseDTO
                .map(this::mapearAResponseDTO)
                // Recolectamos el resultado en una nueva lista
                .toList();
    }

    @Override
    public UsuarioResponseDTO consultarPorId(String id) {
        log.info("Consultando usuario con id: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Usuario no encontrado con id: {}", id);
                    return new UsuarioNoEncontradoException(
                        "No se encontró ningún usuario con el id: " + id
                    );
                });

        return mapearAResponseDTO(usuario);
    }

    @Override
    public void eliminar(String id) {
        log.info("Intentando eliminar usuario con id: {}", id);

        // Verificar existencia antes de eliminar
        if (!usuarioRepository.existsById(id)) {
            log.warn("No se puede eliminar. Usuario no encontrado con id: {}", id);
            throw new UsuarioNoEncontradoException(
                "No se encontró ningún usuario con el id: " + id
            );
        }

        usuarioRepository.deleteById(id);
        log.info("Usuario con id {} eliminado exitosamente", id);
    }

    private UsuarioResponseDTO mapearAResponseDTO(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .telefono(usuario.getTelefono())
                .activo(usuario.getActivo())
                .build();
    }
}
