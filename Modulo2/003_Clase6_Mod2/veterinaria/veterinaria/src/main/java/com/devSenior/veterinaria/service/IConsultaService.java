package com.devSenior.veterinaria.service;

import com.devSenior.veterinaria.model.Consulta;

import java.util.List;

public interface IConsultaService {
    //! Traer todas
    List<Consulta> listarConsultas ();
    //! Crear Mascotas
    void agregarConsulta(Consulta consulta);
}
