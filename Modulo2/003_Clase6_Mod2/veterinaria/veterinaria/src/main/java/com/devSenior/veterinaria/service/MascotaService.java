package com.devSenior.veterinaria.service;

import com.devSenior.veterinaria.model.Mascota;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MascotaService implements IMascotaService{

    private final List<Mascota> mascotas = new ArrayList<>();

    @Override
    public List<Mascota> listarMascotas() {
        return new ArrayList<>(mascotas);
    }

    @Override
    public void agregarMascotas(Mascota mascota) {
        mascotas.add(mascota);
    }
}
