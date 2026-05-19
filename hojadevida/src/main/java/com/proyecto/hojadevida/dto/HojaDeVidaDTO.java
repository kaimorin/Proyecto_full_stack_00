package com.proyecto.hojadevida.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HojaDeVidaDTO {

    @NotBlank(message = "El nombre completo no puede estar vacío")
    private String nombreCompleto;

    @NotNull(message = "La fecha de nacimiento no puede ser nula")
    private Date fechaNacimiento;

    @NotNull (message = "El rut no puede estar vacio")
    @Min(value = 10000000, message = "El rut debe tener mínimo 8 dígitos")
    @Max(value = 999999999, message = "El rut debe tener máximo 89 dígitos")
    private int rutAlumno;

    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccion;

    @Min(value = 100000000, message = "El teléfono debe tener 9 dígitos")
    @Max(value = 999999999, message = "El teléfono debe tener 9 dígitos")
    private int telefonoApoderado;

    @NotBlank(message = "El nombre del apoderado no puede estar vacío")
    private String nombreApoderado;

    @NotBlank(message = "El curso no puede estar vacío")
    private String curso;
}