package com.back.msclientes.domain.model;

public class Cliente extends Persona {

    private final Long clienteId;
    private final String contrasena;
    private final Boolean estado;

    public Cliente(Long clienteId,
                   String nombre,
                   Genero genero,
                   Integer edad,
                   String identificacion,
                   String direccion,
                   String telefono,
                   String contrasena,
                   Boolean estado) {
        super(nombre, genero, edad, identificacion, direccion, telefono);
        this.clienteId = clienteId;
        this.contrasena = contrasena;
        this.estado = estado;
    }

    public Long getClienteId() { return clienteId; }
    public String getContrasena() { return contrasena; }
    public Boolean getEstado() { return estado; }
}