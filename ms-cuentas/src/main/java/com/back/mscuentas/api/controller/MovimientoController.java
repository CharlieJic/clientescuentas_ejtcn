package com.back.mscuentas.api.controller;

import com.back.mscuentas.api.dto.MovimientoCreateRequest;
import com.back.mscuentas.api.dto.MovimientoResponse;
import com.back.mscuentas.application.service.MovimientoService;
import com.back.mscuentas.infrastructure.persistence.entity.MovimientoEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    private final MovimientoService service;

    public MovimientoController(MovimientoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovimientoResponse create(@Valid @RequestBody MovimientoCreateRequest req) {
        return toResponse(service.create(req));
    }

    private MovimientoResponse toResponse(MovimientoEntity m) {
        return new MovimientoResponse(
                m.getMovimientoId(),
                m.getCuenta().getCuentaId(),
                m.getFecha(),
                m.getTipoMovimiento(),
                m.getValor(),
                m.getSaldo()
        );
    }
}