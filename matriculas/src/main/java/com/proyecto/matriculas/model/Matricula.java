package com.proyecto.matriculas.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "matriculas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String runEstudiante;

    @Column(nullable = false)
    private String nombreEstudiante;

    @Column(nullable = false)
    private String curso;

    @Column(nullable = false)
    private String estado;
}