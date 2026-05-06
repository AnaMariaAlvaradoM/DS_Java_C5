package shopSystem.Clase3.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopSystem.Clase3.model.Pedido;
import shopSystem.Clase3.service.IPedidoService;

import java.util.List;


@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final IPedidoService pedidoService;

    public PedidoController(IPedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> pedidosPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(pedidoService.pedidosPorCliente(clienteId));
    }

    @PostMapping("/cliente/{clienteId}")
    public ResponseEntity<?> crearPedido(@PathVariable Long clienteId) {
        try {
            Pedido nuevo = pedidoService.crearPedido(clienteId);
            return ResponseEntity.status(201).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {
        return pedidoService.actualizarEstado(id, estado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        if (pedidoService.eliminar(id)) {
            return ResponseEntity.ok("Pedido eliminado correctamente");
        }
        return ResponseEntity.notFound().build();
    }

    // GET /api/pedidos/ultimo-mes
    @GetMapping("/ultimo-mes")
    public ResponseEntity<List<Pedido>> pedidosUltimoMes() {
        return ResponseEntity.ok(pedidoService.obtenerPedidosUltimoMes());
    }
}