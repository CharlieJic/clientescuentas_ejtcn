package com.back.mscuentas.infrastructure.persistence.repository;

import com.back.mscuentas.infrastructure.persistence.entity.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuentaRepository extends JpaRepository<CuentaEntity, Long> {
    boolean existsByNumeroCuenta(String numeroCuenta);
    Optional<CuentaEntity> findByNumeroCuenta(String numeroCuenta);
}