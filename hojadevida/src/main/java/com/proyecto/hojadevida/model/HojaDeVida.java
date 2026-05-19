package com.proyecto.hojadevida.model;

import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hoja_de_vida")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HojaDeVida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombreCompleto;

    @Column(nullable = false)
    private Date fechaNacimiento;

    @Column(nullable = false, unique = true)
    private int rutAlumno;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private int telefonoApoderado;

    @Column(nullable = false)
    private String nombreApoderado;

    @Column(nullable = false)
    private String curso;
}