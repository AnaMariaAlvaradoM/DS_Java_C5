package com.devsenior.user_mongo.service;

import com.devsenior.user_mongo.DTO.PedidoRequestDTO;
import com.devsenior.user_mongo.DTO.PedidoResponseDTO;

import java.util.List;

public interface IPedidoService {

    PedidoResponseDTO crearPedido(PedidoRequestDTO request);

    PedidoResponseDTO buscarPorId(String id);

    List<PedidoResponseDTO> listarTodos();

    List<PedidoResponseDTO> listarPorUsuario(String usuarioId);

    PedidoResponseDTO actualizarEstado(String id, String nuevoEstado);

    void eliminar(String id);
}