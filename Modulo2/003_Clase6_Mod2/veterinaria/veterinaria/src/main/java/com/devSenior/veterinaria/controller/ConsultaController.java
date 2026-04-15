package com.devSenior.veterinaria.controller;

import com.devSenior.veterinaria.model.Consulta;
import com.devSenior.veterinaria.model.Mascota;
import com.devSenior.veterinaria.service.IConsultaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    private final IConsultaService consultaService;

    public ConsultaController(IConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping
    public ResponseEntity<List<Consulta>> listarConsultas(){
        return ResponseEntity.ok(consultaService.listarConsultas());
    }

    @PostMapping
    public ResponseEntity<String> agregarConsulta(@RequestBody Consulta consulta){
        consultaService.agregarConsulta(consulta);
        return ResponseEntity.status(HttpStatus.CREATED).body("Consulta creada exitosamente");
    }

}
