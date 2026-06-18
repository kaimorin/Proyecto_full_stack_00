package com.proyecto.leccionario.exception;

import com.proyecto.leccionario.dto.ApiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// Maneja las excepciones de toda la aplicación
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Maneja errores de validación
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        400,
                        "Error de validación",
                        errors));
    }

    // Maneja JSON mal formulados
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidJson(
            HttpMessageNotReadableException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        400,
                        "El cuerpo de la solicitud tiene un formato JSON inválido",
                        null));
    }

    // Maneja métodos HTTP incorrectos
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodNotAllowed(
            HttpRequestMethodNotSupportedException ex) {

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ApiResponse<>(
                        405,
                        "Método HTTP no permitido para esta ruta",
                        null));
    }

    // Maneja RuntimeException del Service
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntimeException(
            RuntimeException ex) {

        String message = ex.getMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        int code = 400;

        if (message != null &&
                (message.toLowerCase().contains("no se encontró")
                || message.toLowerCase().contains("no encontrado"))) {

            status = HttpStatus.NOT_FOUND;
            code = 404;
        }

        return ResponseEntity.status(status)
                .body(new ApiResponse<>(
                        code,
                        message,
                        null));
    }

    // Maneja cualquier error no controlado
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