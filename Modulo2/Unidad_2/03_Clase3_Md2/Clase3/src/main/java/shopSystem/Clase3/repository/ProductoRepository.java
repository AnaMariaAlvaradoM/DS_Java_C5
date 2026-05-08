package shopSystem.Clase3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shopSystem.Clase3.model.Producto;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>, ProductoRepositoryC {
    List<Producto>findByNombre(String nombre);
    List<Producto>findByStock(Integer stock);
    List<Producto> findByPrecioLessThan(Double precio);
}
