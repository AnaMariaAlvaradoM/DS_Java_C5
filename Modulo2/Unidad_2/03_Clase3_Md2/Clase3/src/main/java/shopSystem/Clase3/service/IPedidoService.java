package shopSystem.Clase3.service;

import shopSystem.Clase3.model.Pedido;

import java.util.List;
import java.util.Optional;


public interface IPedidoService {
    List<Pedido> listarTodos();
    Optional<Pedido> buscarPorId(Long id);
    List<Pedido> pedidosPorCliente(Long clienteId);
    Pedido crearPedido(Long clienteId);
    Optional<Pedido> actualizarEstado(Long id, String nuevoEstado);
    boolean eliminar(Long id);
}
