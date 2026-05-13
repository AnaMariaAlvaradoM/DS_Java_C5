package com.devsenior.user_mongo.repository;

import com.devsenior.user_mongo.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends MongoRepository<Usuario, String> {


    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
}
