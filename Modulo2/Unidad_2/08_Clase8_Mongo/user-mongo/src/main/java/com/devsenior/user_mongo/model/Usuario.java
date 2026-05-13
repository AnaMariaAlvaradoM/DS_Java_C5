package com.devsenior.user_mongo.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
                                        // ← equivale a @Entity + @Table en JPA
@Data                                   // ← Lombok: genera getters, setters, toString, equals, hashCode
@Builder                                // ← Lombok: permite construir objetos con patrón Builder
@NoArgsConstructor                      // ← Lombok: constructor vacío (requerido por Spring)
@AllArgsConstructor                     // ← Lombok: constructor con todos los campos
public class Usuario {


    @Id
    private String id;

    @Field("nombre")
    private String nombre;
    @Indexed(unique = true)
    @Field("email")
    private String email;


    @Field("telefono")
    private String telefono;

    @Field("activo")
    private Boolean activo;
}
