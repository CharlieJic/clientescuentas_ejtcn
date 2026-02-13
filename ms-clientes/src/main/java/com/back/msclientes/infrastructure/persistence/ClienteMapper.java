package com.back.msclientes.infrastructure.persistence;

import com.back.msclientes.domain.model.Cliente;
import com.back.msclientes.infrastructure.persistence.entity.ClienteEntity;

public class ClienteMapper {

    public static ClienteEntity toEntity(Cliente domain) {
        ClienteEntity e = new ClienteEntity();
        e.setClienteId(domain.getClienteId());
        e.setNombre(domain.getNombre());
        e.setGenero(domain.getGenero());
        e.setEdad(domain.getEdad());
        e.setIdentificacion(domain.getIdentificacion());
        e.setDireccion(domain.getDireccion());
        e.setTelefono(domain.getTelefono());
        e.setContrasena(domain.getContrasena());
        e.setEstado(domain.getEstado());
        return e;
    }

    public static Cliente toDomain(ClienteEntity e) {
        return new Cliente(
                e.getClienteId(),
                e.getNombre(),
                e.getGenero(),
                e.getEdad(),
                e.getIdentificacion(),
                e.getDireccion(),
                e.getTelefono(),
                e.getContrasena(),
                e.getEstado()
        );
    }
}