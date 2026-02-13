package com.back.mscuentas.api.dto;

import java.math.BigDecimal;

public record CuentaResponse(
        Long cuentaId,
        String numeroCuenta,
        String tipoCuenta,
        BigDecimal saldoInicial,
        Boolean estado,
        Long clienteId
) {}