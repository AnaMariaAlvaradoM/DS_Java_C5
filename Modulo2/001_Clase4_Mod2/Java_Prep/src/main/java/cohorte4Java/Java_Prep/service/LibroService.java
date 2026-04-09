package cohorte4Java.Java_Prep.service;

import cohorte4Java.Java_Prep.model.Libro;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroService {

    private final List<Libro> libros = new ArrayList<>();

//    public LibroService() {
//        libros.add(new Libro(1L, "El ingenioso hidalgo Don Quijote", "Miguel de Cervantes", "978-84-376-0494-7", 29.99));
//        libros.add(new Libro(2L, "Cien años de soledad",             "Gabriel García Márquez", "978-0-06-088328-7", 34.50));
//        libros.add(new Libro(3L, "Dune",                             "Frank Herbert",       "978-0-441-01359-7", 40.00));
//        libros.add(new Libro(4L, "El señor de los anillos",          "J.R.R. Tolkien",      "978-84-450-7179-3", 55.00));
//        libros.add(new Libro(5L, "1984",                             "George Orwell",       "978-0-451-52493-5", 22.00));
//    }

    public List<Libro> listarTodos() {
        return libros;
    }

    public Libro buscarPorTitulo(String titulo) {
        return libros.stream()
                     // filter: descarta todos los libros que NO cumplan la condición
                .filter(l -> l.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                     // findFirst: toma el primero que pasó el filtro
                .findFirst()
                     // orElse: si no encontró ninguno, devuelve null
                .orElse(null);
    }

    public void agregar(Libro libro) {
        libros.add(libro);
    }

    public boolean eliminar(Long id) {
        libros.removeIf(l -> l.getId().equals(id));
        return false;
    }

    public List<Libro> buscarPorAutor(String autor) {
        return libros.stream()
                     .filter(l -> l.getAutor().equalsIgnoreCase(autor))
                     .collect(Collectors.toList());
    }

    public boolean actualizarPrecio(Long id, Double nuevoPrecio) {
        return libros.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .map(l -> {
                l.setPrecio(nuevoPrecio);
                return true;
                })
                .orElse(false);
    }

    public String agregarConValidacion(Libro libro) {
        // Regla 1: el título es obligatorio y no puede ser solo espacios
        if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
            return "ERROR: el título no puede estar vacío";
        }
        // Regla 2: el autor es obligatorio
        if (libro.getAutor() == null || libro.getAutor().trim().isEmpty()) {
            return "ERROR: el autor no puede estar vacío";
        }
        // Regla 3: el precio debe existir y ser positivo
        if (libro.getPrecio() == null || libro.getPrecio() <= 0) {
            return "ERROR: el precio debe ser mayor a 0";
        }
        // Todas las validaciones pasaron: podemos agregar el libro
        libros.add(libro);
        return "Libro agregado correctamente";
    }

    public int contarLibros() {
        return libros.size();
    }
}
