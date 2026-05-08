package shopSystem.Clase3.repository;

import shopSystem.Clase3.model.Producto;
import java.util.List;

public interface ProductoRepositoryC {

    List<Producto> buscarConFiltros(String nombre, Double precioMin, Double precioMax);
}
