package com.example.sesion03_2023.models;

import java.io.Serializable;

public class Alumnos implements Serializable {
    private Integer idAlumno;
    private String nombre;
    private String apellidos;
    private String correo;
    private int carrera_id;

    public Alumnos() {
    }

    public Alumnos(Integer idAlumno, String nombre, String apellidos, String correo, int carrera_id) {
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.carrera_id = carrera_id;
    }

    public Integer getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Integer idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellido) {
        this.apellidos = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getCarrera_id() {
        return carrera_id;
    }

    public void setCarrera_id(int carrera_id) {
        this.carrera_id = carrera_id;
    }
}
