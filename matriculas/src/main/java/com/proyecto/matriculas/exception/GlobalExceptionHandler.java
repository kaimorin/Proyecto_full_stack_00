
package com.proyecto.matriculas.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.extern.slf4j.Slf4j;
import com.proyecto.matriculas.dto.ApiResponse;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //validaciones de campo
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationErrors(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(
                        error.getField(),
                        error.getDefaultMessage()));
        log.warn("Validation errors: {}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        400,
                        "Error de validación",
                        errors));
    }

    //manejo json
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidJson(
            HttpMessageNotReadableException ex) {
        log.warn("Invalid JSON payload: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        400,
                        "Formato JSON inválido",
                        null));
    }

    //metodos http
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodNotAllowed(
            HttpRequestMethodNotSupportedException ex) {
        log.warn("HTTP method not allowed: {}", ex.getMethod());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ApiResponse<>(
                        405,
                        "Método HTTP no permitido",
                        null));
    }

    //base de datos
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {
        log.error("Data integrity violation", ex);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse<>(
                        409,
                        "Conflicto con los datos enviados",
                        null));
    }

    //maneja lo del service y otros errores
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntimeException(
            RuntimeException ex) {
        String message = ex.getMessage();
                if (message == null || message.isBlank()) {
                        message = "Error en la petición";
                }
        HttpStatus status = HttpStatus.BAD_REQUEST;
        int code = 400;
        if (message != null &&
                (message.toLowerCase().contains("no encontrada")
                || message.toLowerCase().contains("no encontrado"))) {
            status = HttpStatus.NOT_FOUND;
            code = 404;
        }
        log.warn("Runtime exception handled (status={}): {}", status, message, ex);
        return ResponseEntity.status(status)
                .body(new ApiResponse<>(
                        code,
                        message,
                        null));
    }

    // Maneja cuando una búsqueda en colecciones/Optional falla
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse<Object>> handleNoSuchElement(NoSuchElementException ex) {
                String message = ex.getMessage();
                if (message == null || message.isBlank()) {
                        message = "Recurso no encontrado";
                }
                log.warn("Resource not found: {}", message);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ApiResponse<>(404, message, null));
    }

    // Maneja argumentos inválidos lanzados desde servicios o controladores
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgument(IllegalArgumentException ex) {
                String message = ex.getMessage();
                if (message == null || message.isBlank()) {
                        message = "Argumento inválido";
                }
                log.warn("Illegal argument: {}", message);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ApiResponse<>(400, message, null));
    }

    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(
            Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(
                        500,
                        "Error interno del servidor",
                        null));
    }
}