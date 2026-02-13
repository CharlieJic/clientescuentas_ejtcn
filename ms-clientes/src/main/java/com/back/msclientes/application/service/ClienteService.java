package com.back.msclientes.application.service;

import com.back.msclientes.api.dto.ClienteCreateRequest;
import com.back.msclientes.api.dto.ClientePatchRequest;
import com.back.msclientes.api.dto.ClienteUpdateRequest;
import com.back.msclientes.domain.model.Cliente;
import com.back.msclientes.infrastructure.messaging.ClienteEventPublisher;
import com.back.msclientes.infrastructure.messaging.event.ClienteCreatedEvent;
import com.back.msclientes.infrastructure.persistence.ClientePersistenceAdapter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClientePersistenceAdapter persistence;
    private final ClienteEventPublisher eventPublisher;

    public ClienteService(ClientePersistenceAdapter persistence,
                          ClienteEventPublisher eventPublisher) {
        this.persistence = persistence;
        this.eventPublisher = eventPublisher;
    }

    public Cliente create(ClienteCreateRequest req) {
        Cliente cliente = new Cliente(
                null,
                req.nombre(),
                req.genero(),
                req.edad(),
                req.identificacion(),
                req.direccion(),
                req.telefono(),
                req.contrasena(),
                req.estado()
        );

        Cliente saved = persistence.create(cliente);

        // Evento asíncrono para ms-cuentas (sin exponer contraseña)
        eventPublisher.publishClienteCreated(
                new ClienteCreatedEvent(
                        saved.getClienteId(),
                        saved.getNombre(),
                        saved.getGenero(),
                        saved.getEdad(),
                        saved.getIdentificacion(),
                        saved.getDireccion(),
                        saved.getTelefono(),
                        saved.getEstado()
                )
        );

        return saved;
    }

    public Cliente getById(Long id) {
        return persistence.findById(id);
    }

    public List<Cliente> list() {
        return persistence.findAll();
    }

    public Cliente update(Long id, ClienteUpdateRequest req) {
        Cliente cliente = new Cliente(
                id,
                req.nombre(),
                req.genero(),
                req.edad(),
                req.identificacion(),
                req.direccion(),
                req.telefono(),
                req.contrasena(),
                req.estado()
        );
        return persistence.update(id, cliente);
    }

    public Cliente patch(Long id, ClientePatchRequest req) {
        Cliente current = persistence.findById(id);

        Cliente merged = new Cliente(
                current.getClienteId(),
                req.nombre() != null ? req.nombre() : current.getNombre(),
                req.genero() != null ? req.genero() : current.getGenero(),
                req.edad() != null ? req.edad() : current.getEdad(),
                req.identificacion() != null ? req.identificacion() : current.getIdentificacion(),
                req.direccion() != null ? req.direccion() : current.getDireccion(),
                req.telefono() != null ? req.telefono() : current.getTelefono(),
                req.contrasena() != null ? req.contrasena() : current.getContrasena(),
                req.estado() != null ? req.estado() : current.getEstado()
        );

        return persistence.update(id, merged);
    }

    public void delete(Long id) {
        persistence.delete(id);
    }

    public Cliente deactivate(Long id) {
        return persistence.deactivate(id);
    }
}