package cohorte4Java.Java_Prep.controller;

import cohorte4Java.Java_Prep.model.Libro;
import cohorte4Java.Java_Prep.service.LibroService;
// ↑ Importamos LibroService (clase concreta) — esto es lo que vamos a cambiar en clase
//   cuando apliquemos el principio DIP creando ILibroService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController le dice a Spring que esta clase atiende peticiones HTTP
//                 y que cada método retorna datos (no vistas HTML).
// @RequestMapping("/api/libros") define el prefijo de todas las rutas de esta clase.
// Responsabilidad única: recibir peticiones, llamar al service y devolver la respuesta HTTP.
// No valida datos de negocio — eso es responsabilidad de LibroService.
// No accede a datos — eso es responsabilidad de LibroRepository.
// Analogía: la recepción de la librería — atiende al cliente y le pasa el pedido
//           al empleado (service), pero no procesa nada por sí misma.

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    // Dependemos de LibroService (clase concreta) — esto cambiará en clase.
    // Durante el Bloque 4 vamos a reemplazar esto por ILibroService (interfaz)
    // aplicando el principio DIP: depender de abstracciones, no de implementaciones.
    @Autowired
    private LibroService libroService;

    // GET /api/libros
    // Retorna 200 OK con la lista de libros, o 204 No Content si no hay ninguno.
    // Técnico: ResponseEntity<T> nos permite controlar el código de estado HTTP de la respuesta.
    @GetMapping
    public ResponseEntity<List<Libro>> listarTodos() {
        try {
            List<Libro> libros = libroService.listarTodos();
            if (libros.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 — éxito pero sin contenido
            }
            return ResponseEntity.ok(libros); // 200 — éxito con contenido
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
        }
    }

    // GET /api/libros/buscar?titulo=quijote
    // Retorna 200 si lo encuentra, 404 si no existe.
    @GetMapping("/buscar")
    public ResponseEntity<Libro> buscarPorTitulo(@RequestParam String titulo) {
        try {
            Libro libro = libroService.buscarPorTitulo(titulo);
            if (libro == null) {
                return ResponseEntity.notFound().build(); // 404 — no encontrado
            }
            return ResponseEntity.ok(libro); // 200
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
        }
    }

    // POST /api/libros
    // Retorna 201 Created si se agrega, 400 Bad Request si la validación falla.
    // Técnico: @RequestBody convierte el JSON del cuerpo de la petición en un objeto Libro.
    //          IllegalArgumentException viene del service cuando los datos son inválidos.
    @PostMapping
    public ResponseEntity<String> agregar(@RequestBody Libro libro) {
        try {
            libroService.agregarConValidacion(libro);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Libro agregado correctamente"); // 201
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 400 con mensaje
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al agregar el libro"); // 500
        }
    }

    // DELETE /api/libros/{id}
    // Retorna 200 si se eliminó, 404 si no existía el ID.
    // Técnico: @PathVariable extrae el {id} de la URL y lo convierte a Long.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        try {
            boolean eliminado = libroService.eliminar(id);
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró ningún libro con id " + id); // 404
            }
            return ResponseEntity.ok("Libro con id " + id + " eliminado correctamente"); // 200
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el libro"); // 500
        }
    }

    // GET /api/libros/autor?nombre=Tolkien
    // Retorna 200 con lista, 204 si no hay libros de ese autor.
    @GetMapping("/autor")
    public ResponseEntity<List<Libro>> buscarPorAutor(@RequestParam String nombre) {
        try {
            List<Libro> libros = libroService.buscarPorAutor(nombre);
            if (libros.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204
            }
            return ResponseEntity.ok(libros); // 200
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
        }
    }

    // PUT /api/libros/{id}/precio?valor=49.99
    // Retorna 200 si actualiza, 404 si no existe, 400 si el precio es inválido.
    @PutMapping("/{id}/precio")
    public ResponseEntity<String> actualizarPrecio(@PathVariable Long id,
                                                   @RequestParam Double valor) {
        try {
            if (valor == null || valor < 0) {
                return ResponseEntity.badRequest()
                        .body("El precio debe ser un valor positivo"); // 400
            }
            boolean actualizado = libroService.actualizarPrecio(id, valor);
            if (!actualizado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró ningún libro con id " + id); // 404
            }
            return ResponseEntity.ok("Precio del libro " + id + " actualizado a $" + valor); // 200
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el precio"); // 500
        }
    }

    // GET /api/libros/total
    // Retorna 200 siempre — el conteo puede ser 0, eso no es un error.
    @GetMapping("/total")
    public ResponseEntity<String> contarLibros() {
        try {
            int total = libroService.contarLibros();
            return ResponseEntity.ok("Total de libros registrados: " + total); // 200
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al contar los libros"); // 500
        }
    }
}