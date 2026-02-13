package com.back.mscuentas.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimientoResponse(
        Long movimientoId,
        Long cuentaId,
        LocalDateTime fecha,
        String tipoMovimiento,
        BigDecimal valor,
        BigDecimal saldo
) {}