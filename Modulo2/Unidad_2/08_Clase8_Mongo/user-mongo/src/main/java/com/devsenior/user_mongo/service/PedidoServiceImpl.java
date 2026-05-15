package com.devsenior.user_mongo.service;

import com.devsenior.user_mongo.DTO.PedidoRequestDTO;
import com.devsenior.user_mongo.DTO.PedidoResponseDTO;
import com.devsenior.user_mongo.exception.UsuarioNoEncontradoException;
import com.devsenior.user_mongo.model.Pedido;
import com.devsenior.user_mongo.model.Usuario;
import com.devsenior.user_mongo.repository.IPedidoRepository;
import com.devsenior.user_mongo.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements IPedidoService {

    private final IPedidoRepository pedidoRepository;
    private final IUsuarioRepository usuarioRepository;

    @Override
    public PedidoResponseDTO crear(PedidoRequestDTO dto) {

        // 1. Buscar el usuario. Si no existe, lanza excepción controlada.
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new UsuarioNoEncontradoException(
                        "No se encontró un usuario con el id: " + dto.getUsuarioId()));

        // 2. Construir el pedido.
        //    - id: lo genera MongoDB (no lo asignamos)
        //    - fechaCreacion: la asignamos nosotros con LocalDateTime.now()
        //    - estado: siempre inicia en PENDIENTE
        //    - direccionEntrega: viene del DTO tal como la envió el cliente
        //    - usuario: referencia al documento Usuario encontrado (@DBRef)
        Pedido nuevoPedido = Pedido.builder()
                .descripcion(dto.getDescripcion())
                .total(dto.getTotal())
                .fechaCreacion(LocalDateTime.now())
                .estado("PENDIENTE")
                .direccionEntrega(dto.getDireccionEntrega())
                .usuario(usuario)
                .build();

        // 3. Guardar. Spring Data + @DBRef guarda la referencia al usuario automáticamente.
        Pedido pedidoGuardado = pedidoRepository.save(nuevoPedido);

        // 4. Mapear y retornar el DTO de respuesta.
        return mapearAResponseDTO(pedidoGuardado);
    }

    @Override
    public List<PedidoResponseDTO> listarTodos() {
        return pedidoRepository.findAll()
                .stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PedidoResponseDTO buscarPorId(String id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(
                        "No se encontró un pedido con el id: " + id));
        return mapearAResponseDTO(pedido);
    }

    @Override
    public List<PedidoResponseDTO> buscarPorUsuario(String usuarioId) {
        // Usa el método derivado del repositorio
        return pedidoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PedidoResponseDTO> buscarPorEstado(String estado) {
        return pedidoRepository.findByEstado(estado)
                .stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PedidoResponseDTO> buscarPorCiudad(String ciudad) {
        // Usa la @Query con punto para acceder al campo anidado
        return pedidoRepository.findByDireccionEntregaCiudad(ciudad)
                .stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PedidoResponseDTO> buscarPorDescripcion(String texto) {
        // Usa la @Query con $regex — tarea de los estudiantes
        return pedidoRepository.findByDescripcionContiene(texto)
                .stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(String id) {
        if (!pedidoRepository.existsById(id)) {
            throw new UsuarioNoEncontradoException(
                    "No se encontró un pedido con el id: " + id);
        }
        pedidoRepository.deleteById(id);
    }

    // ── Método auxiliar privado ──────────────────────────────────────────────
    // Convierte un Pedido (entidad) a PedidoResponseDTO.
    // Extrae solo nombre y email del usuario referenciado — no expone el objeto completo.
    private PedidoResponseDTO mapearAResponseDTO(Pedido pedido) {
        return PedidoResponseDTO.builder()
                .id(pedido.getId())
                .descripcion(pedido.getDescripcion())
                .total(pedido.getTotal())
                .fechaCreacion(pedido.getFechaCreacion())
                .estado(pedido.getEstado())
                .direccionEntrega(pedido.getDireccionEntrega())
                .usuarioNombre(pedido.getUsuario() != null ? pedido.getUsuario().getNombre() : null)
                .usuarioEmail(pedido.getUsuario() != null ? pedido.getUsuario().getEmail() : null)
                .build();
    }
}