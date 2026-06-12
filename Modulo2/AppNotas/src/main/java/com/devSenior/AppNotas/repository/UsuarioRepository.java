package com.devSenior.AppNotas.repository;

import com.devPrubea.demo.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * UsuarioRepository — repositorio Mongo de usuarios.
 *
 * Unica diferencia con ShopSystem: extiende MongoRepository en vez de JpaRepository.
 * El <Usuario, String> indica: documento Usuario, con id de tipo String.
 * El metodo findByUsername lo genera Spring Data solo, igual que en JPA.
 */
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    Optional<Usuario> findByUsername(String username);
}