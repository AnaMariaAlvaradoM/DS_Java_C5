package com.devSenior.veterinaria.model;

public class Consulta {

    private String nombreMascota;
    private String motivo;
    private String veterinario;
    private String fecha;

    public Consulta() {
    }

    public Consulta(String nombreMascota, String motivo, String veterinario, String fecha) {
        this.nombreMascota = nombreMascota;
        this.motivo = motivo;
        this.veterinario = veterinario;
        this.fecha = fecha;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
