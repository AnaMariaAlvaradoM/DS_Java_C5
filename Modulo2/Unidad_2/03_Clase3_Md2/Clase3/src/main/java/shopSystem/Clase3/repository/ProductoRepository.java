package shopSystem.Clase3.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shopSystem.Clase3.model.Producto;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>, ProductoRepositoryC {
    List<Producto>findByNombre(String nombre);
    List<Producto>findByStock(Integer stock);
    List<Producto> findByPrecioLessThan(Double precio);
    @Modifying(clearAutomatically = true)
    @Transactional //! o todos en la cama, o todos en el piso
    @Query("UPDATE Producto p SET p.stock = :stock WHERE p.id = :id")
    int actualizarStock(@Param("id") Long id, @Param("stock") Integer stock);
}
