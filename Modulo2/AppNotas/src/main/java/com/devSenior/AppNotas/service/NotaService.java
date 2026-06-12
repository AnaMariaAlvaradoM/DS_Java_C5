package com.devSenior.AppNotas.service;


import com.devPrubea.demo.model.Nota;

import java.util.List;

/**
 * NotaService — contrato de operaciones sobre notas.
 *
 * Cada metodo recibe el username de quien hace la peticion y su rol,
 * porque la logica de acceso (ver todo vs ver lo mio) depende de eso.
 */
public interface NotaService {

    // Crea una nota para el usuario dado
    Nota crear(Nota nota, String username);

    // Lista las notas que el usuario tiene permitido ver
    List<Nota> listarMisNotas(String username);

    // Lista TODAS las notas del sistema (solo lo usara el ADMIN)
    List<Nota> listarTodas();

    // Obtiene una nota por id, validando que el usuario pueda verla
    Nota obtenerPorId(String id, String username, boolean esAdmin);

    // Elimina una nota, validando que el usuario sea su dueño o ADMIN
    void eliminar(String id, String username, boolean esAdmin);
}