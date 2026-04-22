package com.devsenior.vetclinicdb.config;

import com.devsenior.vetclinicdb.model.Mascota;
import com.devsenior.vetclinicdb.model.Propietario;
import com.devsenior.vetclinicdb.repository.MascotaRepository;
import com.devsenior.vetclinicdb.repository.PropietarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner cargarDatos(PropietarioRepository propietarioRepo,
                                   MascotaRepository mascotaRepo) {
        return args -> {

            // Solo insertamos si la tabla está vacía
            if (propietarioRepo.count() == 0) {

                Propietario ana = propietarioRepo.save(
                        new Propietario("Ana Gómez", "3001234567", "ana@email.com"));

                Propietario carlos = propietarioRepo.save(
                        new Propietario("Carlos Ruiz", "3109876543", "carlos@email.com"));

                mascotaRepo.save(new Mascota("Mila",    "Perro", "Golden Retriever", 3, ana));
                mascotaRepo.save(new Mascota("Martina", "Perro", "Labrador",         5, ana));
                mascotaRepo.save(new Mascota("Michi",   "Gato",  "Siamés",           2, carlos));

                System.out.println("Datos de prueba cargados correctamente.");
            }
        };
    }
}
