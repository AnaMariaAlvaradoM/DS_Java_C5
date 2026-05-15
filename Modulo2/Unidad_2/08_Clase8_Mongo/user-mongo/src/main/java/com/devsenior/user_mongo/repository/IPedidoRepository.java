package com.devsenior.user_mongo.repository;

import com.devsenior.user_mongo.model.Pedido;
import org.springframework.data.mongodb.repository.MongoRepository;
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
}