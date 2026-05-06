package shopSystem.Clase3.service;

import shopSystem.Clase3.model.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {
    List<Producto> listarTodos();
    Optional<Producto> buscarPorId(Long id);
    List<Producto> listarDisponibles();
    Producto guardar(Producto producto);
    Optional<Producto> actualizar(Long id, Producto datosNuevos);
    boolean eliminar(Long id);
}