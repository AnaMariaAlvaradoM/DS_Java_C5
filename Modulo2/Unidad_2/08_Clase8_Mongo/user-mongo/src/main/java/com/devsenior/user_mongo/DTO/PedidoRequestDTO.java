package com.devsenior.user_mongo.DTO;

import com.devsenior.user_mongo.model.DireccionEntrega;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequestDTO {

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "El total es obligatorio")
    @Positive(message = "El total debe ser mayor a cero")
    private Double total;

    @NotBlank(message = "El id del usuario es obligatorio")
    private String usuarioId;

    @NotNull(message = "La dirección de entrega es obligatoria")
    @Valid
    private DireccionEntrega direccionEntrega;
}
