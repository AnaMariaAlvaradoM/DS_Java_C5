package cohorte4Java.Java_Prep.service;

import cohorte4Java.Java_Prep.model.Libro;

import java.util.List;

// ILibroService define el CONTRATO de lo que puede hacer el servicio de libros.
// Técnico: es una interfaz — no tiene implementación, solo firmas de métodos.
//          Cualquier clase que implemente esta interfaz está obligada a definir todos estos métodos.
// Analogía: es el manual de funciones del empleado de la librería —
//           define qué debe saber hacer, sin importar cómo lo haga.
//
// ¿Por qué existe esta interfaz? → Principio DIP (Dependency Inversion Principle)
// LibroController dependerá de ILibroService, no de LibroService directamente.
// Si mañana creamos LibroServiceV2, el controller no necesita cambiar.

public interface ILibroService {

    // Retorna todos los libros registrados en el sistema
    List<Libro> listarTodos();

    // Busca un libro cuyo título contenga el texto dado — retorna null si no existe
    Libro buscarPorTitulo(String titulo);

    // Retorna todos los libros de un autor específico
    List<Libro> buscarPorAutor(String autor);

    // Valida y agrega un libro — lanza IllegalArgumentException si los datos son inválidos
    Libro agregarConValidacion(Libro libro);

    // Elimina un libro por ID — retorna true si existía, false si no se encontró
    boolean eliminar(Long id);

    // Actualiza el precio de un libro — retorna true si existía, false si no
    boolean actualizarPrecio(Long id, Double nuevoPrecio);

    // Retorna el total de libros registrados
    int contarLibros();

    Libro actualizarLibro(Long id, Libro libroNuevo);
}