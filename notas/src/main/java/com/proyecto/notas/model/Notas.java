package com.proyecto.notas.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "notas")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del estudiante no puede estar vacio")
    @Column(nullable = false)
    private String estudiante;

    @NotBlank(message = "La asignatura es obligatoria")
    @Column(nullable = false)
    private String asignatura;
    
    @NotNull(message = "La nota es obligatoria")
    @DecimalMin(value = "1.0", message = "La nota mínima es 1.0")
    @DecimalMax(value = "7.0", message = "La nota máxima es 7.0")
    @Column(nullable = false)
    private Double valor;
}


