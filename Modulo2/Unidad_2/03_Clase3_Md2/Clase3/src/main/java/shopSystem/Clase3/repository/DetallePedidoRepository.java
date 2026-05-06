package shopSystem.Clase3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shopSystem.Clase3.model.DetallePedido;

import java.util.List;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    List<DetallePedido> findByPedidoId(Long pedidoId);
    @Query("SELECT d.producto, SUM(d.cantidad) " +
            "FROM DetallePedido d " +
            "GROUP BY d.producto " +
            "ORDER BY SUM(d.cantidad) DESC")
    List<Object[]> findProductosMasVendidos();
}
