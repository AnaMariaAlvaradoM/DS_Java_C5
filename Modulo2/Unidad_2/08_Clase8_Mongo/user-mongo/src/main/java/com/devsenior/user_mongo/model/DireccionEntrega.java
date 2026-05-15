package com.devsenior.user_mongo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DireccionEntrega {

    @Field("calle")
    private String calle;

    @Field("ciudad")
    private String ciudad;

    @Field("departamento")
    private String departamento;

    @Field("codigo_postal")  // camelCase → snake_case
    private String codigoPostal;
}