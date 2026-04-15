package cohorte4Java.Java_Prep.service;

import cohorte4Java.Java_Prep.model.Libro;
import cohorte4Java.Java_Prep.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// @Service le dice a Spring que esta clase contiene la lógica de negocio.
// Responsabilidad única: aplicar las reglas del sistema sobre los libros (SRP).
// No sabe cómo se guardan los datos — eso es responsabilidad de LibroRepository.
// No sabe cómo se responde al cliente — eso es responsabilidad de LibroController.
// Analogía: el empleado de la librería que conoce las reglas del negocio,
// pero le pide al almacén (LibroRepository) que busque o guarde los libros físicamente.

@Service
public class LibroService {

    // LibroService necesita LibroRepository para acceder a los datos.
    // final = esta referencia no cambia después de que Spring la inyecta.
    private final LibroRepository libroRepository;

    // Inyección por constructor — Spring detecta el parámetro y busca automáticamente
    // un @Repository del tipo LibroRepository para pasarlo aquí.
    // Analogía: al contratar al empleado, la empresa le asigna el acceso al almacén.
    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    // Retorna todos los libros — delega directamente al repository.
    // El service no necesita lógica adicional aquí: listar es responsabilidad del repository.
    public List<Libro> listarTodos() {
        return libroRepository.findAll();
    }

    // Busca un libro cuyo título contenga el texto dado (búsqueda parcial, sin importar mayúsculas).
    // Técnico: Stream con filter y findFirst. Retorna null si no encuentra.
    //          El controller convierte ese null en una respuesta 404 Not Found.
    // Analogía: buscar en el catálogo por palabra clave, no por título exacto.
    public Libro buscarPorTitulo(String titulo) {
        return libroRepository.findAll().stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    // Busca todos los libros de un autor específico (sin importar mayúsculas).
    // Técnico: filter con equalsIgnoreCase + collect para reunir los resultados en una lista.
    // Analogía: buscar en el catálogo todos los libros de un mismo escritor.
    public List<Libro> buscarPorAutor(String autor) {
        return libroRepository.findAll().stream()
                .filter(l -> l.getAutor().equalsIgnoreCase(autor))
                .collect(Collectors.toList());
    }

    // Valida y agrega un libro aplicando las reglas de negocio.
    // Técnico: las validaciones viven en el service, no en el controller —
    //          son reglas del sistema, no reglas de presentación.
    //          Si algo falla, lanza IllegalArgumentException para que el controller responda 400.
    // Analogía: el empleado verifica que el libro cumpla los requisitos antes de registrarlo.
    public Libro agregarConValidacion(Libro libro) {
        // Regla 1: el título es obligatorio
        if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        // Regla 2: el autor es obligatorio
        if (libro.getAutor() == null || libro.getAutor().trim().isEmpty()) {
            throw new IllegalArgumentException("El autor no puede estar vacío");
        }
        // Regla 3: el precio debe existir y ser positivo
        if (libro.getPrecio() == null || libro.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
        // Todas las validaciones pasaron — delega el guardado al repository
        return libroRepository.save(libro);
    }

    // Elimina un libro por ID — retorna true si existía, false si no se encontró.
    // Técnico: delega a LibroRepository.deleteById() que ya maneja la lógica de existencia
    //          y retorna el resultado correcto (true/false).
    // NOTA: el código original tenía un bug aquí — siempre retornaba false aunque
    //       el libro existiera. Ya está corregido en esta versión.
    public boolean eliminar(Long id) {
        return libroRepository.deleteById(id);
    }

    // Actualiza el precio de un libro si existe.
    // Técnico: findById retorna Optional — isPresent() verifica si se encontró algo
    //          antes de intentar modificarlo. Si no existe, retorna false.
    // Analogía: buscar el libro en el inventario y cambiarle la etiqueta de precio.
    public boolean actualizarPrecio(Long id, Double nuevoPrecio) {
        Optional<Libro> encontrado = libroRepository.findById(id);
        if (encontrado.isPresent()) {
            encontrado.get().setPrecio(nuevoPrecio);
            return true;
        }
        return false;
    }

    // Retorna el total de libros registrados.
    // Técnico: delega al repository y retorna el tamaño de la lista.
    public int contarLibros() {
        return libroRepository.findAll().size();
    }


    public Libro actualizar(Long id, Libro nuevo) {
        Optional<Libro> encontrado =
                libroRepository.findById(id);
        if (encontrado.isPresent()) {
            Libro existente = encontrado.get();
            existente.setTitulo(nuevo.getTitulo());
            existente.setAutor(nuevo.getAutor());
            existente.setIsbn(nuevo.getIsbn());
            existente.setPrecio(nuevo.getPrecio());
            return existente;
        }
        return null; // controller → 404
    }
}