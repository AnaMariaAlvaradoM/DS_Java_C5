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

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements IPedidoService {

    private final IPedidoRepository pedidoRepository;
    private final IUsuarioRepository usuarioRepository;

    @Override
    public PedidoResponseDTO crearPedido(PedidoRequestDTO request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new UsuarioNoEncontradoException(
                        "Usuario no encontrado con id: " + request.getUsuarioId()));

        Pedido pedido = Pedido.builder()
                .descripcion(request.getDescripcion())
                .total(request.getTotal())
                .direccionEntrega(request.getDireccionEntrega())
                .usuarioId(request.getUsuarioId())
                .estado("PENDIENTE")
                .fechaCreacion(LocalDateTime.now())
                .build();

        Pedido guardado = pedidoRepository.save(pedido);
        return toResponse(guardado, usuario);
    }

    @Override
    public PedidoResponseDTO buscarPorId(String id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));

        Usuario usuario = usuarioRepository.findById(pedido.getUsuarioId())
                .orElseThrow(() -> new UsuarioNoEncontradoException(
                        "Usuario no encontrado con id: " + pedido.getUsuarioId()));

        return toResponse(pedido, usuario);
    }

    @Override
    public List<PedidoResponseDTO> listarTodos() {
        return pedidoRepository.findAll()
                .stream()
                .map(pedido -> {
                    Usuario usuario = usuarioRepository.findById(pedido.getUsuarioId())
                            .orElseThrow(() -> new UsuarioNoEncontradoException(
                                    "Usuario no encontrado con id: " + pedido.getUsuarioId()));
                    return toResponse(pedido, usuario);
                })
                .toList();
    }

    @Override
    public List<PedidoResponseDTO> listarPorUsuario(String usuarioId) {
        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNoEncontradoException(
                        "Usuario no encontrado con id: " + usuarioId));

        return pedidoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(pedido -> {
                    Usuario usuario = usuarioRepository.findById(pedido.getUsuarioId()).orElseThrow();
                    return toResponse(pedido, usuario);
                })
                .toList();
    }

    @Override
    public PedidoResponseDTO actualizarEstado(String id, String nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));

        pedido.setEstado(nuevoEstado);
        Pedido actualizado = pedidoRepository.save(pedido);

        Usuario usuario = usuarioRepository.findById(pedido.getUsuarioId())
                .orElseThrow(() -> new UsuarioNoEncontradoException(
                        "Usuario no encontrado con id: " + pedido.getUsuarioId()));

        return toResponse(actualizado, usuario);
    }

    @Override
    public void eliminar(String id) {
        if (!pedidoRepository.existsById(id)) {
            throw new RuntimeException("Pedido no encontrado con id: " + id);
        }
        pedidoRepository.deleteById(id);
    }

    // ── Mapper privado ──────────────────────────────────────────────────────────
    private PedidoResponseDTO toResponse(Pedido pedido, Usuario usuario) {
        return PedidoResponseDTO.builder()
                .id(pedido.getId())
                .descripcion(pedido.getDescripcion())
                .total(pedido.getTotal())
                .fechaCreacion(pedido.getFechaCreacion())
                .estado(pedido.getEstado())
                .direccionEntrega(pedido.getDireccionEntrega())
                .usuarioNombre(usuario.getNombre())
                .usuarioEmail(usuario.getEmail())
                .build();
    }
}