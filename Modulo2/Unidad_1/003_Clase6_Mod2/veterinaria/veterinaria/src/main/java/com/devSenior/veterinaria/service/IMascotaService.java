package com.devSenior.veterinaria.service;

import com.devSenior.veterinaria.model.Mascota;

import java.util.List;

public interface IMascotaService {
    //! Traer todos
    List<Mascota> listarMascotas();
    //! Crear Mascotas
    void agregarMascotas(Mascota mascota);
}
