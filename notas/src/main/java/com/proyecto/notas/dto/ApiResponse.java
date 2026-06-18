package com.proyecto.notas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contenedor genérico para enviar respuestas estructuradas desde la API.
 *
 * Usa un código de estado interno, un mensaje legible y el contenido de la respuesta.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;
}
