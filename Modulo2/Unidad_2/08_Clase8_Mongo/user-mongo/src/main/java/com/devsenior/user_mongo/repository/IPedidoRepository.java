package com.devsenior.user_mongo.repository;

import com.devsenior.user_mongo.model.Pedido;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPedidoRepository extends MongoRepository<Pedido, String> {

    // Método derivado: todos los pedidos de un usuario específico
    List<Pedido> findByUsuarioId(String usuarioId);

    // Método derivado: todos los pedidos con un estado específico
    List<Pedido> findByEstado(String estado);

    // Método derivado: pedidos cuyo total es mayor a un valor dado
    List<Pedido> findByTotalGreaterThan(Double total);

    // Campo anidado: el "." accede dentro del subdocumento
    @Query("{ 'direccionEntrega.ciudad': ?0 }")
    List<Pedido> findByDireccionEntregaCiudad(String ciudad);

    // Regex: busca el texto en "descripcion", sin importar mayúsculas
    @Query("{ 'descripcion': { $regex: ?0, $options: 'i' } }")
    List<Pedido> findByDescripcionContiene(String texto);

    // Combinada: ciudad + total mayor que un valor ($gt = greater than)
    @Query("{ 'direccionEntrega.ciudad': ?0, 'total': { $gt: ?1 } }")
    List<Pedido> findByCiudadAndTotalGreaterThan(
            String ciudad, Double total);

}