package com.back.mscuentas.application.service;

import com.back.mscuentas.api.dto.ReporteResponse;
import com.back.mscuentas.domain.exception.BusinessException;
import com.back.mscuentas.domain.exception.NotFoundException;
import com.back.mscuentas.infrastructure.persistence.entity.ClienteViewEntity;
import com.back.mscuentas.infrastructure.persistence.entity.CuentaEntity;
import com.back.mscuentas.infrastructure.persistence.entity.MovimientoEntity;
import com.back.mscuentas.infrastructure.persistence.repository.ClienteViewRepository;
import com.back.mscuentas.infrastructure.persistence.repository.CuentaRepository;
import com.back.mscuentas.infrastructure.persistence.repository.MovimientoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ReporteService {

    private final ClienteViewRepository clienteViewRepository;
    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;

    public ReporteService(ClienteViewRepository clienteViewRepository,
                          CuentaRepository cuentaRepository,
                          MovimientoRepository movimientoRepository) {
        this.clienteViewRepository = clienteViewRepository;
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
    }

    public List<ReporteResponse> generar(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {

        if (fechaInicio.isAfter(fechaFin)) {
            throw new BusinessException("fechaInicio no puede ser mayor que fechaFin");
        }

        ClienteViewEntity cliente = clienteViewRepository.findById(clienteId)
                .orElseThrow(() -> new NotFoundException("cliente no encontrado (no existe en ms-cuentas)"));

        // rango inclusivo
        LocalDateTime desde = fechaInicio.atStartOfDay();
        LocalDateTime hasta = fechaFin.plusDays(1).atStartOfDay().minusNanos(1);

        List<MovimientoEntity> movimientos = movimientoRepository
                .findByCuenta_ClienteIdAndFechaBetweenOrderByFechaAsc(clienteId, desde, hasta);

        // cache para saldoInicial/tipoCuenta/estado/numeroCuenta por cuenta (evita N+1)
        Map<Long, CuentaEntity> cuentaCache = new HashMap<>();

        List<ReporteResponse> out = new ArrayList<>();

        for (MovimientoEntity m : movimientos) {
            CuentaEntity cuenta = cuentaCache.computeIfAbsent(
                    m.getCuenta().getCuentaId(),
                    id -> cuentaRepository.findById(id).orElse(null)
            );

            if (cuenta == null) continue;

            BigDecimal movimientoSigned = signedMovimiento(m);

            out.add(new ReporteResponse(
                    m.getFecha().toLocalDate(),
                    cliente.getNombre(),
                    cuenta.getNumeroCuenta(),
                    cuenta.getTipoCuenta(),
                    cuenta.getSaldoInicial(),
                    cuenta.getEstado(),
                    movimientoSigned,
                    m.getSaldo()
            ));
        }

        return out;
    }

    private BigDecimal signedMovimiento(MovimientoEntity m) {
        if ("RETIRO".equals(m.getTipoMovimiento())) {
            return m.getValor().negate();
        }
        return m.getValor();
    }
}