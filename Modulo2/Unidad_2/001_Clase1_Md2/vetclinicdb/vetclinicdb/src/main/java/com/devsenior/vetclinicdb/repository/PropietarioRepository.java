package com.devsenior.vetclinicdb.repository;

import com.devsenior.vetclinicdb.model.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PropietarioRepository extends JpaRepository<Propietario, Long> {

    Optional<Propietario> findByEmail(String email);

    boolean existsByEmail(String email);
}
