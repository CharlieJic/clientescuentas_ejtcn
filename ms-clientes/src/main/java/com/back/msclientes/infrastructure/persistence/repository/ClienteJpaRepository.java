package com.back.msclientes.infrastructure.persistence.repository;

import com.back.msclientes.infrastructure.persistence.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> findByIdentificacion(String identificacion);
    boolean existsByIdentificacion(String identificacion);
    Page<ClienteEntity> findAll(Pageable pageable);
}