package com.proyecto.notificaciones.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.proyecto.notificaciones.dto.ApiResponse;

/**
 * Manejador global de excepciones para el microservicio.
 *
 * Captura errores comunes de validación y HTTP y devuelve respuestas
 * estructuradas con ApiResponse.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja los errores de validación de DTOs cuando @Valid falla.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(),
                                error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        400,
                        "Error de validación",
                        errors));
    }

    /**
     * Maneja solicitudes JSON mal formadas o inválidas.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidJson(
            HttpMessageNotReadableException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        400,
                        "El formato JSON es inválido",
                        null));
    }

    /**
     * Maneja intentos de usar un método HTTP no permitido en el endpoint.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodNotAllowed(
            HttpRequestMethodNotSupportedException ex) {

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ApiResponse<>(
                        405,
                        "Método HTTP no se permite",
                        null));
    }

    /**
     * Maneja excepciones de tiempo de ejecución y devuelve un error genérico.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntimeException(
            RuntimeException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        400,
                        ex.getMessage(),
                        null));
    }

    /**
     * Captura cualquier excepción no prevista y devuelve un error interno.
     */
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