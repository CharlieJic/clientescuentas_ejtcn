package com.back.mscuentas.api.controller;

import com.back.mscuentas.api.dto.CuentaCreateRequest;
import com.back.mscuentas.api.dto.CuentaResponse;
import com.back.mscuentas.application.service.CuentaService;
import com.back.mscuentas.infrastructure.persistence.entity.CuentaEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.back.mscuentas.api.dto.CuentaPatchRequest;
import com.back.mscuentas.api.dto.CuentaUpdateRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    private final CuentaService service;

    public CuentaController(CuentaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CuentaResponse create(@Valid @RequestBody CuentaCreateRequest req) {
        return toResponse(service.create(req));
    }

    @GetMapping("/{id}")
    public CuentaResponse getById(@PathVariable Long id) {
        return toResponse(service.getById(id));
    }

    @GetMapping
    public List<CuentaResponse> list() {
        return service.list().stream().map(this::toResponse).toList();
    }

    @PutMapping("/{id}")
    public CuentaResponse update(@PathVariable Long id, @Valid @RequestBody CuentaUpdateRequest req) {
        return toResponse(service.update(id, req));
    }

    @PatchMapping("/{id}")
    public CuentaResponse patch(@PathVariable Long id, @Valid @RequestBody CuentaPatchRequest req) {
        return toResponse(service.patch(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private CuentaResponse toResponse(CuentaEntity e) {
        return new CuentaResponse(
                e.getCuentaId(),
                e.getNumeroCuenta(),
                e.getTipoCuenta(),
                e.getSaldoInicial(),
                e.getEstado(),
                e.getClienteId()
        );
    }
}