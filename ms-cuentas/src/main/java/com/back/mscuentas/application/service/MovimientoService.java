package com.back.mscuentas.application.service;

import com.back.mscuentas.api.dto.MovimientoCreateRequest;
import com.back.mscuentas.domain.exception.BusinessException;
import com.back.mscuentas.domain.exception.NotFoundException;
import com.back.mscuentas.infrastructure.persistence.entity.CuentaEntity;
import com.back.mscuentas.infrastructure.persistence.entity.MovimientoEntity;
import com.back.mscuentas.infrastructure.persistence.repository.CuentaRepository;
import com.back.mscuentas.infrastructure.persistence.repository.MovimientoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class MovimientoService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;

    public MovimientoService(CuentaRepository cuentaRepository, MovimientoRepository movimientoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
    }

    @Transactional
    public MovimientoEntity create(MovimientoCreateRequest req) {
        CuentaEntity cuenta = cuentaRepository.findById(req.cuentaId())
                .orElseThrow(() -> new NotFoundException("cuenta no encontrada"));

        if (!Boolean.TRUE.equals(cuenta.getEstado())) {
            throw new BusinessException("cuenta inactiva");
        }

        // saldo actual = último movimiento.saldo o saldoInicial si no hay movimientos
        BigDecimal saldoActual = movimientoRepository.findByCuenta_CuentaIdOrderByFechaAsc(cuenta.getCuentaId())
                .stream()
                .reduce((first, second) -> second)
                .map(MovimientoEntity::getSaldo)
                .orElse(cuenta.getSaldoInicial());

        BigDecimal valor = req.valor();
        BigDecimal saldoNuevo;

        if ("RETIRO".equals(req.tipoMovimiento())) {
            saldoNuevo = saldoActual.subtract(valor);
            if (saldoNuevo.compareTo(BigDecimal.ZERO) < 0) {
                // requisito clave
                throw new BusinessException("Saldo no disponible");
            }
        } else {
            saldoNuevo = saldoActual.add(valor);
        }

        MovimientoEntity m = new MovimientoEntity();
        m.setCuenta(cuenta);
        m.setFecha(LocalDateTime.now());
        m.setTipoMovimiento(req.tipoMovimiento());
        m.setValor(valor);
        m.setSaldo(saldoNuevo);

        return movimientoRepository.save(m);
    }
}