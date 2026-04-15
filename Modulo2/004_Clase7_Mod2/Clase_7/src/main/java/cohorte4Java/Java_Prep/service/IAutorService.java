package cohorte4Java.Java_Prep.service;

import cohorte4Java.Java_Prep.model.Autor;

import java.util.List;
import java.util.Optional;

public interface IAutorService {

    List<Autor> listarAutores();

    Optional<Autor> buscarPorId(Long idAutor);

    Autor agregar(Autor autor);

    Autor actualizar(long id, Autor autor);

    boolean eliminar(long id);

    int contarAutores();
}
