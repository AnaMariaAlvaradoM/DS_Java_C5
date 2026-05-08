package shopSystem.Clase3.service;

import shopSystem.Clase3.model.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {
    List<Producto> listarTodos();
    Optional<Producto> buscarPorId(Long id);
    List<Producto> listarDisponibles();
    List<Producto> obtenerProductosMenoresDe(Double precio);
    Producto guardar(Producto producto);
    Optional<Producto> actualizar(Long id, Producto datosNuevos);
    boolean eliminar(Long id);
    List<Object[]> obtenerProductosMasVendidos();
    List<Producto> buscarConFiltros(String nombre, Double precioMin, Double precioMax);
    boolean actualizarStock(Long id, Integer nuevoStock);

}