package com.devSenior.veterinaria.service;

import com.devSenior.veterinaria.model.Consulta;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultaService implements  IConsultaService{

    private final List<Consulta> consultas = new ArrayList<>();
    @Override
    public List<Consulta> listarConsultas() {
        return new ArrayList<>(consultas);
    }

    @Override
    public void agregarConsulta(Consulta consulta) {
        consultas.add(consulta);
    }
}
