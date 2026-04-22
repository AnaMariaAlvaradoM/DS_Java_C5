package com.devsenior.vetclinicdb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "propietarios")
public class Propietario {

    // TODO: ¿Qué anotaciones necesita este campo para ser la llave primaria?
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: ¿Qué validación le pondrías al nombre? ¿Puede estar vacío?
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 100)
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotBlank(message = "El teléfono es obligatorio")
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Email(message = "El correo no tiene un formato válido")
    @Column(name = "email", unique = true)
    private String email;

    // Constructor vacío requerido por JPA
    // ¿Por qué JPA necesita un constructor sin argumentos?
    public Propietario() {}

    public Propietario(String nombre, String telefono, String email) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    // --- Getters y Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Propietario{id=" + id + ", nombre='" + nombre + "', email='" + email + "'}";
    }
}
