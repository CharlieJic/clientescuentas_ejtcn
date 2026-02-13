package com.back.mscuentas.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReporteResponse(
        LocalDate fecha,
        String cliente,
        String numeroCuenta,
        String tipoCuenta,
        BigDecimal saldoInicial,
        Boolean estado,
        BigDecimal movimiento,
        BigDecimal saldoDisponible
) {}