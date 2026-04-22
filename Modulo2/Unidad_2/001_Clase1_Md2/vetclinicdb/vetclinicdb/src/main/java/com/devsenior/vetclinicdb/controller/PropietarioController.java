package com.devsenior.vetclinicdb.controller;

import com.devsenior.vetclinicdb.model.Propietario;
import com.devsenior.vetclinicdb.service.PropietarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/propietarios")
public class PropietarioController {

    private final PropietarioService propietarioService;

    public PropietarioController(PropietarioService propietarioService) {
        this.propietarioService = propietarioService;
    }

    // GET /api/propietarios
    @GetMapping
    public List<Propietario> listarTodos() {
        return propietarioService.listarTodos();
    }

    // GET /api/propietarios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Propietario> buscarPorId(@PathVariable Long id) {
        return propietarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/propietarios
    @PostMapping
    public ResponseEntity<Propietario> crear(@Valid @RequestBody Propietario propietario) {
        Propietario guardado = propietarioService.guardar(propietario);
        return ResponseEntity.ok(guardado);
    }

    // DELETE /api/propietarios/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        propietarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
