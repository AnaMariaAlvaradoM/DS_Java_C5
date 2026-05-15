package com.devsenior.user_mongo.controller;

import com.devsenior.user_mongo.DTO.UsuarioRequestDTO;
import com.devsenior.user_mongo.DTO.UsuarioResponseDTO;
import com.devsenior.user_mongo.service.IUsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final IUsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> registrar(
            @Valid @RequestBody UsuarioRequestDTO dto) {

        UsuarioResponseDTO respuesta = usuarioService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> consultarTodos() {
        return ResponseEntity.ok(usuarioService.consultarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> consultarPorId(
            @PathVariable String id) {

        return ResponseEntity.ok(usuarioService.consultarPorId(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(
            @PathVariable String id,
            @Valid @RequestBody UsuarioRequestDTO dto) {

        UsuarioResponseDTO respuesta = usuarioService.actualizar(id, dto);
        // HTTP 200 OK: la actualización fue exitosa y devolvemos el recurso actualizado
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable String id) {

        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
