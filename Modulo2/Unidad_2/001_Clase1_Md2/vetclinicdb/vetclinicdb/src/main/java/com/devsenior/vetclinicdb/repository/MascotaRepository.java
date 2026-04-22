package com.devsenior.vetclinicdb.repository;

import com.devsenior.vetclinicdb.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {

    // Trae todas las mascotas de un propietario específico
    // → SELECT * FROM mascotas WHERE propietario_id = ?
    List<Mascota> findByPropietarioId(Long propietarioId);

    // Busca mascotas por especie (sin importar mayúsculas)
    // → SELECT * FROM mascotas WHERE LOWER(especie) = LOWER(?)
    List<Mascota> findByEspecieIgnoreCase(String especie);
}
