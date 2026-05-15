package com.devsenior.user_mongo.controller;

import com.devsenior.user_mongo.DTO.PedidoRequestDTO;
import com.devsenior.user_mongo.DTO.PedidoResponseDTO;
import com.devsenior.user_mongo.service.IPedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final IPedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crearPedido(
            @Valid @RequestBody PedidoRequestDTO dto) {

        PedidoResponseDTO respuesta = pedidoService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(
            @PathVariable String id) {

        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorUsuario(
            @PathVariable String usuarioId) {

        return ResponseEntity.ok(pedidoService.buscarPorUsuario(usuarioId));
    }
//
//    @PatchMapping("/{id}/estado")
//    public ResponseEntity<PedidoResponseDTO> actualizarEstado(
//            @PathVariable String id,
//            @RequestParam String nuevoEstado) {
//
//        return ResponseEntity.ok(pedidoService.(id, nuevoEstado));
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable String id) {

        pedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}