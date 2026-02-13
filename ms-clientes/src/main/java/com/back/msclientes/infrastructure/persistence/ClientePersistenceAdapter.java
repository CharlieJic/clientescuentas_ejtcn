package com.back.msclientes.infrastructure.persistence;

import com.back.msclientes.domain.exception.ConflictException;
import com.back.msclientes.domain.exception.NotFoundException;
import com.back.msclientes.domain.model.Cliente;
import com.back.msclientes.infrastructure.persistence.entity.ClienteEntity;
import com.back.msclientes.infrastructure.persistence.repository.ClienteJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ClientePersistenceAdapter {

    private final ClienteJpaRepository repo;

    public ClientePersistenceAdapter(ClienteJpaRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Cliente create(Cliente cliente) {
        if (repo.existsByIdentificacion(cliente.getIdentificacion())) {
            throw new ConflictException("identificacion ya existe");
        }
        ClienteEntity saved = repo.save(ClienteMapper.toEntity(cliente));
        return ClienteMapper.toDomain(saved);
    }

    public Cliente findById(Long id) {
        ClienteEntity e = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("cliente no encontrado"));
        return ClienteMapper.toDomain(e);
    }

    public List<Cliente> findAll() {
        return repo.findAll().stream().map(ClienteMapper::toDomain).toList();
    }

    @Transactional
    public Cliente update(Long id, Cliente cliente) {
        ClienteEntity existing = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("cliente no encontrado"));

        // Si cambió identificación, validar duplicados
        if (!existing.getIdentificacion().equals(cliente.getIdentificacion())
                && repo.existsByIdentificacion(cliente.getIdentificacion())) {
            throw new ConflictException("identificacion ya existe");
        }

        ClienteEntity toSave = ClienteMapper.toEntity(cliente);
        toSave.setClienteId(existing.getClienteId()); // asegurar ID
        ClienteEntity saved = repo.save(toSave);
        return ClienteMapper.toDomain(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("cliente no encontrado");
        }
        repo.deleteById(id);
    }

    @Transactional
    public Cliente deactivate(Long id) {
        ClienteEntity existing = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("cliente no encontrado"));
        existing.setEstado(false);
        ClienteEntity saved = repo.save(existing);
        return ClienteMapper.toDomain(saved);
    }
}