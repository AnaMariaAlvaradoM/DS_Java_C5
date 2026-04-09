package cohorte4Java.Java_Prep.repository;

import cohorte4Java.Java_Prep.model.Libro;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// @Repository le dice a Spring que esta clase es responsable del acceso a datos.
// Por ahora los datos viven en memoria (un ArrayList).
// Cuando veamos el módulo de bases de datos, este será el único archivo que cambia —
// el service y el controller no se tocan. Eso es exactamente SRP en acción.
// Analogía: es el almacén de la librería — sabe dónde están físicamente los libros,
// pero no conoce las reglas del negocio ni cómo se responde al cliente.

@Repository
public class LibroRepository {

    // Lista en memoria que actúa como nuestra "base de datos" por ahora.
    // final = la referencia a la lista no cambia, aunque su contenido sí puede cambiar.
    private final List<Libro> libros = new ArrayList<>();

    // Constructor: carga datos iniciales para que la API tenga contenido desde el inicio.
    // Analogía: abrir la tienda con los estantes ya llenos el primer día.
    public LibroRepository() {
        libros.add(new Libro(1L, "El ingenioso hidalgo Don Quijote", "Miguel de Cervantes",   "978-84-376-0494-7", 29.99));
        libros.add(new Libro(2L, "Cien años de soledad",             "Gabriel García Márquez","978-0-06-088328-7", 34.50));
        libros.add(new Libro(3L, "Dune",                             "Frank Herbert",         "978-0-441-01359-7", 40.00));
        libros.add(new Libro(4L, "El señor de los anillos",          "J.R.R. Tolkien",        "978-84-450-7179-3", 55.00));
        libros.add(new Libro(5L, "1984",                             "George Orwell",         "978-0-451-52493-5", 22.00));
    }

    // Devuelve todos los libros almacenados.
    // Técnico: retorna la referencia directa a la lista interna.
    // Analogía: mostrar todos los estantes al cliente.
    public List<Libro> findAll() {
        return libros;
    }

    // Busca un libro por su ID — Optional evita retornar null.
    // Técnico: si no existe el ID, retorna Optional.empty() en lugar de null,
    //          obligando al llamador a manejar explícitamente el caso "no encontrado".
    // Analogía: buscar un libro por número de registro — puede estar o no estar.
    public Optional<Libro> findById(Long id) {
        return libros.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst();
    }

    // Guarda un libro nuevo y lo retorna.
    // Técnico: agrega al ArrayList y retorna el mismo objeto para que el llamador lo use.
    // Analogía: registrar un libro nuevo en el inventario de la tienda.
    public Libro save(Libro libro) {
        libros.add(libro);
        return libro;
    }

    // Elimina un libro por ID — retorna true si lo encontró y eliminó, false si no existía.
    // Técnico: removeIf recorre la lista, elimina los que cumplan la condición
    //          y retorna true si eliminó al menos uno.
    // Analogía: retirar un libro del inventario y confirmar si existía o no.
    public boolean deleteById(Long id) {
        return libros.removeIf(l -> l.getId().equals(id));
    }

    // Verifica si existe un libro con ese ID sin cargarlo completo.
    // Técnico: anyMatch recorre la lista y retorna true en cuanto encuentra una coincidencia.
    public boolean existsById(Long id) {
        return libros.stream().anyMatch(l -> l.getId().equals(id));
    }
}