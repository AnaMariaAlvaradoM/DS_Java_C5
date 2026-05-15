package com.devsenior.user_mongo.DTO;

import com.devsenior.user_mongo.model.DireccionEntrega;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDTO {

    private String id;
    private String descripcion;
    private Double total;
    private LocalDateTime fechaCreacion;
    private String estado;
    private DireccionEntrega direccionEntrega;
    private String usuarioNombre;
    private String usuarioEmail;
}

