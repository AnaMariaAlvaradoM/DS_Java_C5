package com.devSenior.AppNotas.repository;

import com.devPrubea.demo.model.Nota;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * NotaRepository — repositorio Mongo de notas.
 *
 * findByUsuarioId: trae solo las notas de un dueño concreto.
 * Es la base del control de ownership (cada quien ve lo suyo).
 */
public interface NotaRepository extends MongoRepository<Nota, String> {

    List<Nota> findByUsuarioId(String usuarioId);
}