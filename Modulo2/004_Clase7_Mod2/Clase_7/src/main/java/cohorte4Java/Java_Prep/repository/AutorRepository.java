package cohorte4Java.Java_Prep.repository;

import cohorte4Java.Java_Prep.model.Autor;
import cohorte4Java.Java_Prep.model.Libro;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class AutorRepository {

    private final List<Autor> autores = new ArrayList<>();

    private final AtomicLong contadorId = new AtomicLong(4);

    // Datos iniciales en constructor
    public AutorRepository() {
        autores.add(new Autor(1L, "Cervantes",
                "Española", "c@letras.es"));
    }

    // findAll, findById, save,
    // update, deleteById

    public List<Autor> findAll(){
        return autores;
    }

    public Optional<Autor> findById(Long id){
        return autores.stream()
                .filter(a -> a.getIdAutor().equals(id))
                .findFirst();
    }

    public Autor save(Autor autor) {
        autores.add(autor);
        return autor;
    }

    public boolean deleteById(Long id) {
        return autores.removeIf(a -> a.getIdAutor().equals(id));
    }

    public Autor update(Long id, Autor autorNuevo) {
        Optional<Autor> encontrado = findById(id);
        if (encontrado.isPresent()) {
            Autor existente = encontrado.get();
            existente.setNombre(autorNuevo.getNombre());
            existente.setNacionalidad(autorNuevo.getNacionalidad());
            existente.setEmail(autorNuevo.getEmail());
            return existente;
        }
        return null;
    }





}

