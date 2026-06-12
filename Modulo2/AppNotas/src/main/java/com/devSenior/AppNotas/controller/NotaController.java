package com.devSenior.AppNotas.controller;


import com.devPrubea.demo.model.Nota;
import com.devPrubea.demo.service.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * NotaController — endpoints REST de notas con control de acceso.
 *
 * Dos tipos de control conviven:
 *  - Por ROL:   @PreAuthorize("hasRole('ADMIN')") en /todas
 *  - Por DUEÑO: el resto pasa el username al servicio para filtrar por usuarioId
 */
@RestController
@RequestMapping("/api/notas")
public class NotaController {

    @Autowired
    private NotaService notaService;

    // Helper: detecta si quien hace la peticion es ADMIN mirando sus authorities
    private boolean esAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    /**
     * POST /api/notas — crea una nota. La nota queda a nombre del usuario autenticado.
     * Cualquier usuario autenticado (USER o ADMIN) puede crear.
     */
    @PostMapping
    public ResponseEntity<Nota> crear(@RequestBody Nota nota, Authentication auth) {
        Nota creada = notaService.crear(nota, auth.getName());
        return ResponseEntity.ok(creada);
    }

    /**
     * GET /api/notas — lista MIS notas (las del usuario autenticado).
     */
    @GetMapping
    public ResponseEntity<List<Nota>> misNotas(Authentication auth) {
        return ResponseEntity.ok(notaService.listarMisNotas(auth.getName()));
    }

    /**
     * GET /api/notas/todas — lista TODAS las notas del sistema.
     * Solo ADMIN: lo garantiza @PreAuthorize a nivel de rol.
     */
    @GetMapping("/todas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Nota>> todas() {
        return ResponseEntity.ok(notaService.listarTodas());
    }

    /**
     * GET /api/notas/{id} — obtiene una nota.
     * El servicio valida que sea del usuario o que sea ADMIN.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Nota> obtener(@PathVariable String id, Authentication auth) {
        Nota nota = notaService.obtenerPorId(id, auth.getName(), esAdmin(auth));
        return ResponseEntity.ok(nota);
    }

    /**
     * DELETE /api/notas/{id} — elimina una nota.
     * El servicio valida que sea del usuario o que sea ADMIN.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable String id, Authentication auth) {
        notaService.eliminar(id, auth.getName(), esAdmin(auth));
        return ResponseEntity.ok("Nota eliminada");
    }
}