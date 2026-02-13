package com.back.mscuentas.api.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CuentaCreateRequest(

        @NotBlank(message = "numeroCuenta es obligatorio")
        @Size(min = 6, max = 20, message = "numeroCuenta debe tener entre 6 y 20 dígitos")
        @Pattern(regexp = "^[0-9]+$", message = "numeroCuenta solo puede contener números")
        String numeroCuenta,

        @NotBlank(message = "tipoCuenta es obligatorio")
        @Pattern(regexp = "^(AHORROS|CORRIENTE)$", message = "tipoCuenta debe ser AHORROS o CORRIENTE")
        String tipoCuenta,

        @NotNull(message = "saldoInicial es obligatorio")
        @DecimalMin(value = "0.00", inclusive = true, message = "saldoInicial no puede ser negativo")
        BigDecimal saldoInicial,

        @NotNull(message = "estado es obligatorio")
        Boolean estado,

        @NotNull(message = "clienteId es obligatorio")
        @Min(value = 1, message = "clienteId debe ser mayor a 0")
        Long clienteId
) {}