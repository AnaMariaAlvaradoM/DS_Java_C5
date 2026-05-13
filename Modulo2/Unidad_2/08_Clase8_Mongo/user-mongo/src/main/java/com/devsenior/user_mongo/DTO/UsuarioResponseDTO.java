package com.devsenior.user_mongo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {

   private String id;

    private String nombre;

    private String email;

    private String telefono;

    private Boolean activo;
}
