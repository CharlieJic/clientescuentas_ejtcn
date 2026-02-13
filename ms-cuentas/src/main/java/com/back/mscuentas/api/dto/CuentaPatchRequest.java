package com.back.mscuentas.api.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CuentaPatchRequest(

        @Size(min = 6, max = 20, message = "numeroCuenta debe tener entre 6 y 20 dígitos")
        @Pattern(regexp = "^[0-9]+$", message = "numeroCuenta solo puede contener números")
        String numeroCuenta,

        @Pattern(regexp = "^(AHORROS|CORRIENTE)$", message = "tipoCuenta debe ser AHORROS o CORRIENTE")
        String tipoCuenta,

        @DecimalMin(value = "0.00", inclusive = true, message = "saldoInicial no puede ser negativo")
        BigDecimal saldoInicial,

        Boolean estado,

        @Min(value = 1, message = "clienteId debe ser mayor a 0")
        Long clienteId
) {}