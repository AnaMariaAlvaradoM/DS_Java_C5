package com.devSenior.veterinaria.controller;


import com.devSenior.veterinaria.model.Mascota;
import com.devSenior.veterinaria.service.IMascotaService;
import com.devSenior.veterinaria.service.MascotaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final IMascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @GetMapping
    public ResponseEntity<List<Mascota>> listarMascotas(){
        List<Mascota> mascotas = mascotaService.listarMascotas();
        return ResponseEntity.ok(mascotas);
    }

    @PostMapping
    public ResponseEntity<String> agregarMascota(@RequestBody Mascota mascota){
        mascotaService.agregarMascotas(mascota);
        return ResponseEntity.status(HttpStatus.CREATED).body("Mascota creada exitosamente");
    }

}
