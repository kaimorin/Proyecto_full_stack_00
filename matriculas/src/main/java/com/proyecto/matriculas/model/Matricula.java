package com.proyecto.matriculas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "matriculas")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String runEstudiante;
    private String nombreEstudiante;
    private String curso;
    private String estado;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRunEstudiante() { return runEstudiante; }
    public void setRunEstudiante(String runEstudiante) { this.runEstudiante = runEstudiante; }

    public String getNombreEstudiante() { return nombreEstudiante; }
    public void setNombreEstudiante(String nombreEstudiante) { this.nombreEstudiante = nombreEstudiante; }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}