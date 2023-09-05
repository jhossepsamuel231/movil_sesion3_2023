package com.example.sesion03_2023.models;

import java.io.Serializable;

public class Carrera implements Serializable {
    private Integer idCarrera;
    private String nombre;
    private Boolean estado;

    public Carrera() {
    }

    public Carrera(Integer idCarrera, String nombre, Boolean estado) {
        this.idCarrera = idCarrera;
        this.nombre = nombre;
        this.estado = estado;
    }

    public Integer getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(Integer idCarrera) {
        this.idCarrera = idCarrera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
