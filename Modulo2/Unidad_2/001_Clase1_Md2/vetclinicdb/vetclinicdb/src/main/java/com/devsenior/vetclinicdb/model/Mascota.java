package com.devsenior.vetclinicdb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;


@Entity
@Table(name = "mascotas")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la mascota es obligatorio")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotBlank(message = "La especie es obligatoria")
    @Column(name = "especie", nullable = false)
    private String especie; // Ej: "Perro", "Gato", "Conejo"

    @Column(name = "raza")
    private String raza;

    @Positive(message = "La edad debe ser un número positivo")
    @Column(name = "edad")
    private Integer edad;

    /**
     * Relación: Muchas mascotas → Un propietario
     *
     * @ManyToOne indica la cardinalidad de la relación.
     * @JoinColumn le dice a JPA el nombre de la columna FK en esta tabla.
     * En la BD quedará: mascotas.propietario_id → propietarios.id
     *
     * PREGUNTA: ¿Qué pasaría si borramos un propietario que tiene mascotas?
     * → Introduce el concepto de cascade y orphanRemoval (se verá en clase 3)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propietario_id", nullable = false)
    private Propietario propietario;

    // Constructor vacío – requerido por JPA
    public Mascota() {}

    public Mascota(String nombre, String especie, String raza, Integer edad, Propietario propietario) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.propietario = propietario;
    }

    // --- Getters y Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }

    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }

    public Propietario getPropietario() { return propietario; }
    public void setPropietario(Propietario propietario) { this.propietario = propietario; }

    @Override
    public String toString() {
        return "Mascota{id=" + id + ", nombre='" + nombre + "', especie='" + especie + "'}";
    }
}
