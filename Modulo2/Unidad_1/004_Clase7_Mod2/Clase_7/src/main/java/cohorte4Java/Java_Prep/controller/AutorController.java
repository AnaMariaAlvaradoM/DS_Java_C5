package cohorte4Java.Java_Prep.controller;


import cohorte4Java.Java_Prep.model.Autor;
import cohorte4Java.Java_Prep.service.IAutorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

        private final IAutorService autorService;

        public AutorController(IAutorService autorService) {
            this.autorService = autorService;
        }

        @GetMapping
        public ResponseEntity<List<Autor>> listarTodos() {
            try {
                List<Autor> autores = autorService.listarAutores();
                if (autores.isEmpty()) {
                    return ResponseEntity.noContent().build(); // 204 — éxito pero sin contenido
                }
                return ResponseEntity.ok(autores); // 200 — éxito con la lista
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
            }
        }

        @GetMapping("/{id}")
        public ResponseEntity<Autor> buscarPorId(@PathVariable Long id) {
            try {
                return autorService.buscarPorId(id)
                        .map(ResponseEntity::ok)                    // si existe → 200 con el autor
                        .orElse(ResponseEntity.notFound().build()); // si no → 404 Not Found
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
            }
        }


        @PostMapping
        public ResponseEntity<String> agregar(@Valid @RequestBody Autor autor) {
            try {
                autorService.agregar(autor);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body("Autor agregado correctamente"); // 201 Created
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(e.getMessage()); // 400 con el mensaje
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error al agregar el autor"); // 500
            }
        }

        @PutMapping("/{id}")
        public ResponseEntity<Autor> actualizar(@PathVariable Long id,
                                                @Valid @RequestBody Autor autor) {
            try {
                Autor actualizado = autorService.actualizar(id, autor);
                if (actualizado == null) {
                    return ResponseEntity.notFound().build(); // 404 — no existe ese ID
                }
                return ResponseEntity.ok(actualizado); // 200 con el autor actualizado
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> eliminar(@PathVariable Long id) {
            try {
                boolean eliminado = autorService.eliminar(id);
                if (!eliminado) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("No se encontró ningún autor con id " + id); // 404
                }
                return ResponseEntity.ok("Autor con id " + id + " eliminado correctamente"); // 200
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error al eliminar el autor"); // 500
            }
        }

        @GetMapping("/total")
        public ResponseEntity<String> contarAutores() {
            try {
                int total = autorService.contarAutores();
                return ResponseEntity.ok("Total de autores registrados: " + total); // 200
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error al contar los autores"); // 500
            }
        }
    }

