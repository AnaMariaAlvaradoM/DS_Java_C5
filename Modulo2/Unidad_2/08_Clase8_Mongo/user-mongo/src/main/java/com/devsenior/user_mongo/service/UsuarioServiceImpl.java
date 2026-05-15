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

    @Override
    public UsuarioResponseDTO actualizar(String id, UsuarioRequestDTO dto) {
        // 1. Buscar el usuario existente. Si no existe, lanza la excepción que ya tenemos.
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(
                        "No se encontró un usuario con el id: " + id));

        // 2. Si el email cambió, verificar que el nuevo email no esté en uso por otro usuario.
        if (!usuarioExistente.getEmail().equals(dto.getEmail())) {
            if (usuarioRepository.existsByEmail(dto.getEmail())) {
                throw new EmailDuplicadoException(
                        "Ya existe un usuario registrado con el email: " + dto.getEmail());
            }
        }

        // 3. Actualizar los campos con los datos del DTO.
        //    El id y el campo 'activo' no se tocan — vienen del objeto ya guardado.
        usuarioExistente.setNombre(dto.getNombre());
        usuarioExistente.setEmail(dto.getEmail());
        usuarioExistente.setTelefono(dto.getTelefono());

        // 4. Guardar. Como el objeto ya tiene un id, MongoDB actualiza el documento existente.
        //    No crea uno nuevo. Es el mismo comportamiento que en JPA.
        Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);

        // 5. Retornar el DTO de respuesta.
        return mapearAResponseDTO(usuarioActualizado);
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
