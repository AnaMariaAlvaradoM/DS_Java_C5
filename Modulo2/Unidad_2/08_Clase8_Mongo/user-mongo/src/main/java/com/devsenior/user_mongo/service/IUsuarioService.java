package com.devsenior.user_mongo.service;
import com.devsenior.user_mongo.DTO.UsuarioRequestDTO;
import com.devsenior.user_mongo.DTO.UsuarioResponseDTO;

import java.util.List;

public interface IUsuarioService {

    UsuarioResponseDTO registrar(UsuarioRequestDTO dto);

    List<UsuarioResponseDTO> consultarTodos();

    UsuarioResponseDTO consultarPorId(String id);

    void eliminar(String id);
}
