package com.devSenior.veterinaria.model;

public class Mascota {
    private String nombre;
    private String especie;
    private int edad;
    private String dueno;

    public Mascota() {
    }

    public Mascota(String nombre, String especie, int edad, String dueno) {
        this.nombre = nombre;
        this.especie = especie;
        this.edad = edad;
        this.dueno = dueno;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public int getEdad() {
        return edad;
    }

    public String getDueno() {
        return dueno;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setDueno(String dueno) {
        this.dueno = dueno;
    }
}
