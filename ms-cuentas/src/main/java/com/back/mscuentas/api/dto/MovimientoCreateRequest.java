package com.back.mscuentas.api.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record MovimientoCreateRequest(

        @NotNull(message = "cuentaId es obligatorio")
        @Min(value = 1, message = "cuentaId debe ser mayor a 0")
        Long cuentaId,

        @NotBlank(message = "tipoMovimiento es obligatorio")
        @Pattern(regexp = "^(DEPOSITO|RETIRO)$", message = "tipoMovimiento debe ser DEPOSITO o RETIRO")
        String tipoMovimiento,

        @NotNull(message = "valor es obligatorio")
        @DecimalMin(value = "0.01", inclusive = true, message = "valor debe ser mayor a 0")
        BigDecimal valor
) {}