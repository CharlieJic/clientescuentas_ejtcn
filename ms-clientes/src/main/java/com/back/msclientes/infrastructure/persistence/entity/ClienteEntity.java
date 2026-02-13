package com.back.msclientes.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente", indexes = {
        @Index(name = "idx_cliente_identificacion", columnList = "identificacion", unique = true)
})
public class ClienteEntity extends PersonaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clienteId;

    @Column(nullable = false, length = 60)
    private String contrasena;

    @Column(nullable = false)
    private Boolean estado;

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }
}