package com.proyecto.notificaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contenedor genérico para devolver respuestas de API estructuradas.
 *
 * Permite enviar un código, un mensaje y datos opcionales en todas las respuestas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;
}
