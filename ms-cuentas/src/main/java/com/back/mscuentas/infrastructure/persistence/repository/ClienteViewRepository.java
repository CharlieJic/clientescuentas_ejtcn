package com.back.mscuentas.infrastructure.persistence.repository;

import com.back.mscuentas.infrastructure.persistence.entity.ClienteViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteViewRepository extends JpaRepository<ClienteViewEntity, Long> {
    Optional<ClienteViewEntity> findByIdentificacion(String identificacion);
}