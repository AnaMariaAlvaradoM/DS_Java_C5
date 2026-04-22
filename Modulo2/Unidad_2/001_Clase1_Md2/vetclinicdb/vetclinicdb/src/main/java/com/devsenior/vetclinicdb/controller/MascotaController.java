package com.devsenior.vetclinicdb.controller;

import com.devsenior.vetclinicdb.model.Mascota;
import com.devsenior.vetclinicdb.service.MascotaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    // GET /api/mascotas
    @GetMapping
    public List<Mascota> listarTodas() {
        return mascotaService.listarTodas();
    }

    // GET /api/mascotas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Mascota> buscarPorId(@PathVariable Long id) {
        return mascotaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/mascotas/propietario/{propietarioId}
    @GetMapping("/propietario/{propietarioId}")
    public List<Mascota> porPropietario(@PathVariable Long propietarioId) {
        return mascotaService.buscarPorPropietario(propietarioId);
    }

    // GET /api/mascotas/especie/{especie}
    @GetMapping("/especie/{especie}")
    public List<Mascota> porEspecie(@PathVariable String especie) {
        return mascotaService.buscarPorEspecie(especie);
    }

    // POST /api/mascotas
    @PostMapping
    public ResponseEntity<Mascota> crear(@Valid @RequestBody Mascota mascota) {
        Mascota guardada = mascotaService.guardar(mascota);
        return ResponseEntity.ok(guardada);
    }

    // DELETE /api/mascotas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mascotaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
