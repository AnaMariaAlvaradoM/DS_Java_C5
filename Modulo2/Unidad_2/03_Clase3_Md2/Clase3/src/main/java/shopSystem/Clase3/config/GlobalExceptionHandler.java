package shopSystem.Clase3.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shopSystem.Clase3.dto.ErrorResponse;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> manejarAutenticacion(
            AuthenticationException ex,
            HttpServletRequest request) {

        // Construir el objeto de respuesta de error con todos los campos.
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),                   // momento del error
                HttpStatus.UNAUTHORIZED.value(),       // 401
                "Credenciales inválidas o usuario no encontrado", // mensaje descriptivo
                request.getRequestURI()               // ruta que causó el error
        );

        // ResponseEntity permite controlar el código de estado HTTP de la respuesta.
        // .status(HttpStatus.UNAUTHORIZED): establece el código 401 en el encabezado HTTP.
        // .body(error): establece el cuerpo de la respuesta como el objeto ErrorResponse.
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    // @ExceptionHandler(AccessDeniedException.class): maneja los casos donde
    // el usuario está autenticado pero no tiene permiso para la operación.
    // Spring Security lanza AccessDeniedException cuando se viola una regla de autorización
    // — por ejemplo, un CLIENTE que intenta hacer POST en /api/productos.
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> manejarAccesoDenegado(
            AccessDeniedException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),          // 403
                "No tienes permiso para realizar esta operación",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    // Manejador genérico: captura cualquier excepción no manejada por los métodos anteriores.
    // Es el "catch-all" — evita que errores inesperados expongan stack traces completos al cliente.
    // En producción, exponer stack traces es un riesgo de seguridad porque revelan
    // la estructura interna del código a posibles atacantes.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarGeneral(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500
                "Ocurrió un error interno en el servidor",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}

