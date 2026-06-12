package com.devSenior.AppNotas.dto;


/**
 * AuthResponse — el token que devolvemos al iniciar sesion.
 */
public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() { return token; }
}