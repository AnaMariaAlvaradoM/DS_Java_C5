package shopSystem.Clase3.service;

import org.springframework.stereotype.Service;
import shopSystem.Clase3.model.Producto;
import shopSystem.Clase3.repository.ProductoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    public List<Producto> listarDisponibles() {
        return productoRepository.findByStockDisponible(0);
    }

    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public Optional<Producto> actualizar(Long id, Producto datosNuevos) {
        return productoRepository.findById(id).map(productoExistente -> {
            productoExistente.setNombre(datosNuevos.getNombre());
            productoExistente.setPrecio(datosNuevos.getPrecio());
            productoExistente.setStock(datosNuevos.getStock());
            return productoRepository.save(productoExistente);
        });
    }

    public boolean eliminar(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}