package com.devsenior.user_mongo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "pedidos")   // colección propia
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id  // import: org.springframework.data.annotation.Id
    private String id;

    @Field("descripcion")
    private String descripcion;
    @Field("total")
    private Double total;
    @Field("fecha_creacion")
    private LocalDateTime fechaCreacion;
    @Field("estado")
    private String estado;

    // Anidado: sin anotación especial
    private DireccionEntrega direccionEntrega;

    @DBRef  // Referenciado: guarda puntero a colección usuarios
    private Usuario usuario;
}
