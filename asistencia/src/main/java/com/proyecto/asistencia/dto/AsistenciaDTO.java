package com.proyecto.asistencia.dto;

import java.sql.Date;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsistenciaDTO {

    @NotNull(message = "La fecha no puede estar vacía")
    Date fecha;

    @NotBlank (message = "El estado debe ser presente, ausente o atrasado. No puede estar vacio")
    @Size (min = 7, message = "El estado debe tener al menos 7 caracteres")
    String estadoAsistencia;

    @NotNull (message = "El rut no puede estar vacio")
    @Min(value = 10000000, message = "El rut debe tener mínimo 8 dígitos")
    @Max(value = 999999999, message = "El rut debe tener máximo 89 dígitos")
    int rutAlumno;

}
