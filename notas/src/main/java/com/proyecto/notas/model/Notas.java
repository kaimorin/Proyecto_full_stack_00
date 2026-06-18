package com.proyecto.notas.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * Entidad JPA que representa la tabla de notas en la base de datos.
 *
 * Cada instancia corresponde a una fila de la tabla "notas".
 */
@Entity
@Table(name = "notas")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notas {

    /** Identificador único generado automáticamente. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre del estudiante asociado a la nota. */
    @NotBlank(message = "El nombre del estudiante no puede estar vacio")
    @Column(nullable = false)
    private String estudiante;

    /** Nombre de la asignatura evaluada. */
    @NotBlank(message = "La asignatura es obligatoria")
    @Column(nullable = false)
    private String asignatura;
    
    /** Valor de la nota, entre 1.0 y 7.0. */
    @NotNull(message = "La nota es obligatoria")
    @DecimalMin(value = "1.0", message = "La nota mínima es 1.0")
    @DecimalMax(value = "7.0", message = "La nota máxima es 7.0")
    @Column(nullable = false)
    private Double valor;
}


