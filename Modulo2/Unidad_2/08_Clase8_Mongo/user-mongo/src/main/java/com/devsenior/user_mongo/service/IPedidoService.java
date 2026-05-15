package com.devsenior.user_mongo.service;

import com.devsenior.user_mongo.DTO.PedidoRequestDTO;
import com.devsenior.user_mongo.DTO.PedidoResponseDTO;

import java.util.List;

public interface IPedidoService {

    PedidoResponseDTO crear(PedidoRequestDTO dto);

    List<PedidoResponseDTO> listarTodos();

    PedidoResponseDTO buscarPorId(String id);

    List<PedidoResponseDTO> buscarPorUsuario(String usuarioId);

    List<PedidoResponseDTO> buscarPorEstado(String estado);

    List<PedidoResponseDTO> buscarPorCiudad(String ciudad);

    List<PedidoResponseDTO> buscarPorDescripcion(String texto);

    void eliminar(String id);
}