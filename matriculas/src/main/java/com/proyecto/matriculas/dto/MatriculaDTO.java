package com.proyecto.matriculas.dto;

public class MatriculaDTO {

    private String runEstudiante;
    private String nombreEstudiante;
    private String curso;
    private String estado;

    // Getters y Setters
    public String getRunEstudiante() { return runEstudiante; }
    public void setRunEstudiante(String runEstudiante) { this.runEstudiante = runEstudiante; }

    public String getNombreEstudiante() { return nombreEstudiante; }
    public void setNombreEstudiante(String nombreEstudiante) { this.nombreEstudiante = nombreEstudiante; }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}