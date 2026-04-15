package cohorte4Java.Java_Prep.service;

import cohorte4Java.Java_Prep.model.Autor;
import cohorte4Java.Java_Prep.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService implements IAutorService{
    @Autowired
    private final AutorRepository autorRepository;

public AutorService(AutorRepository autorRepository) {
            this.autorRepository = autorRepository;
        }

    @Override
    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    @Override
        public Optional<Autor> buscarPorId(Long id) {
            return autorRepository.findById(id);
        }

    @Override
    public Autor agregar(Autor autor) {
           if (autor.getNombre() == null || autor.getNombre().trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre del autor no puede estar vacío");
            }
            if (autor.getEmail() == null || autor.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("El email del autor es obligatorio");
            }
            if (autor.getNacionalidad() == null || autor.getNacionalidad().trim().isEmpty()) {
                throw new IllegalArgumentException("La nacionalidad es obligatoria");
            }
            return autorRepository.save(autor);
        }

    @Override
    public Autor actualizar(long id, Autor autor) {
        return autorRepository.update(id, autor);
    }

    @Override
    public boolean eliminar(long id) {
        return autorRepository.deleteById(id);
    }


        @Override
        public int contarAutores() {
            return autorRepository.findAll().size();
        }
}
