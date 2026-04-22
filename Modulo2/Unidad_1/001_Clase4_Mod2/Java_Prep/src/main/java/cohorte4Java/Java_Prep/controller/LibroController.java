//package cohorte4Java.Java_Prep.controller;
//
//import cohorte4Java.Java_Prep.model.Libro;
//import cohorte4Java.Java_Prep.service.LibroService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//@RestController
//@RequestMapping("/api/libros")
//public class LibroController {
//
//    @Autowired
//    private LibroService libroService;
//
//    @GetMapping
//    public List<Libro> listarTodos() {
//        return libroService.listarTodos();
//    }
//
//    @GetMapping("/buscar")
//    public Libro buscarPorTitulo(@RequestParam String titulo) {
//        return libroService.buscarPorTitulo(titulo);
//    }
//
//    @PostMapping
//    public String agregar(@RequestBody Libro libro) {
//        return libroService.agregarConValidacion(libro);
//    }
//
//    @DeleteMapping("/{id}")
//    public String eliminar(@PathVariable Long id) {
//        libroService.eliminar(id);
//        return "Libro con id " + id + " eliminado correctamente";
//    }
//
//    @GetMapping("/autor")
//    public List<Libro> buscarPorAutor(@RequestParam String nombre) {
//        return libroService.buscarPorAutor(nombre);
//    }
//
//    @PutMapping("/{id}/precio")
//    public String actualizarPrecio(@PathVariable Long id, @RequestParam Double valor) {
//        boolean actualizado = libroService.actualizarPrecio(id, valor);
//
//        if (actualizado) {
//            return "Precio del libro " + id + " actualizado a $" + valor;
//        }
//        return "No se encontró ningún libro con id " + id;
//    }
//
//    @GetMapping("/total")
//    public String contarLibros() {
//        int total = libroService.contarLibros();
//        return "Total de libros registrados: " + total;
//    }
//}

package cohorte4Java.Java_Prep.controller;

import cohorte4Java.Java_Prep.model.Libro;
import cohorte4Java.Java_Prep.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    // 200 OK con la lista, o 204 si no hay libros
    @GetMapping
    public ResponseEntity<List<Libro>> listarTodos() {
        try {
            List<Libro> libros = libroService.listarTodos();
            if (libros.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204
            }
            return ResponseEntity.ok(libros); // 200
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
        }
    }

    // 200 si lo encuentra, 404 si no existe
    @GetMapping("/buscar")
    public ResponseEntity<Libro> buscarPorTitulo(@RequestParam String titulo) {
        try {
            Libro libro = libroService.buscarPorTitulo(titulo);
            if (libro == null) {
                return ResponseEntity.notFound().build(); // 404
            }
            return ResponseEntity.ok(libro); // 200
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
        }
    }

    // 201 si se crea, 400 si la validación falla
    @PostMapping
    public ResponseEntity<String> agregar(@RequestBody Libro libro) {
        try {
            String resultado = libroService.agregarConValidacion(libro);
            return ResponseEntity.status(HttpStatus.CREATED).body(resultado); // 201
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 400
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al agregar el libro"); // 500
        }
    }

    // 200 si se elimina, 404 si no existe el id
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

    // 200 con lista, 204 si no hay resultados para ese autor
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

    // 200 si actualiza, 404 si no existe, 400 si el valor es inválido
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

    // 200 siempre, el conteo puede ser 0
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
