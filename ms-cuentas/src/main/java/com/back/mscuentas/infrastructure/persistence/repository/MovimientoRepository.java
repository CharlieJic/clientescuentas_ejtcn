package com.back.mscuentas.infrastructure.persistence.repository;

import com.back.mscuentas.infrastructure.persistence.entity.MovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<MovimientoEntity, Long> {

    List<MovimientoEntity> findByCuenta_CuentaIdOrderByFechaAsc(Long cuentaId);

    List<MovimientoEntity> findByCuenta_ClienteIdAndFechaBetweenOrderByFechaAsc(
            Long clienteId, LocalDateTime desde, LocalDateTime hasta
    );
}