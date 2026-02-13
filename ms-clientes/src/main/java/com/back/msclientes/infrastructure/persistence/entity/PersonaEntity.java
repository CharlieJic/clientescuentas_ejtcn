package com.back.msclientes.infrastructure.persistence.entity;

import com.back.msclientes.domain.model.Genero;
import jakarta.persistence.*;

@MappedSuperclass
public abstract class PersonaEntity {

    @Column(nullable = false, length = 80)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Genero genero;

    @Column(nullable = false)
    private Integer edad;

    @Column(nullable = false, length = 20)
    private String identificacion;

    @Column(nullable = false, length = 120)
    private String direccion;

    @Column(nullable = false, length = 15)
    private String telefono;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Genero getGenero() { return genero; }
    public void setGenero(Genero genero) { this.genero = genero; }

    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }

    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}