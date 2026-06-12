package com.devSenior.AppNotas.dto;

import com.devPrubea.demo.model.Rol;

/**
 * AuthRequest — datos para registro y login.
 * El campo rol solo se usa en el registro (en login se ignora).
 */
public class AuthRequest {
    private String username;
    private String password;
    private Rol rol;   // opcional: para elegir ADMIN o USER al registrar

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}