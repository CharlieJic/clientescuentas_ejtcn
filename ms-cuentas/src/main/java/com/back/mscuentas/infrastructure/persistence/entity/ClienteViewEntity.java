package com.back.mscuentas.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente_view")
public class ClienteViewEntity {

    @Id
    @Column(name = "cliente_id")
    private Long clienteId;

    @Column(nullable = false, length = 80)
    private String nombre;

    @Column(nullable = false, length = 1)
    private String genero;

    @Column(nullable = false)
    private Integer edad;

    @Column(nullable = false, unique = true, length = 20)
    private String identificacion;

    @Column(nullable = false, length = 120)
    private String direccion;

    @Column(nullable = false, length = 15)
    private String telefono;

    @Column(nullable = false)
    private Boolean estado;

    public ClienteViewEntity() {}

    // getters/setters (puedes usar Lombok si lo habilitaste)
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }

    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }
}