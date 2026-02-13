package com.back.mscuentas.application.service;

import com.back.mscuentas.api.dto.CuentaCreateRequest;
import com.back.mscuentas.domain.exception.ConflictException;
import com.back.mscuentas.domain.exception.NotFoundException;
import com.back.mscuentas.infrastructure.persistence.entity.CuentaEntity;
import com.back.mscuentas.infrastructure.persistence.repository.ClienteViewRepository;
import com.back.mscuentas.infrastructure.persistence.repository.CuentaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteViewRepository clienteViewRepository;

    public CuentaService(CuentaRepository cuentaRepository, ClienteViewRepository clienteViewRepository) {
        this.cuentaRepository = cuentaRepository;
        this.clienteViewRepository = clienteViewRepository;
    }

    public CuentaEntity create(CuentaCreateRequest req) {

        if (!clienteViewRepository.existsById(req.clienteId())) {
            throw new NotFoundException("cliente no existe en ms-cuentas (no ha llegado el evento)");
        }

        if (cuentaRepository.existsByNumeroCuenta(req.numeroCuenta())) {
            throw new ConflictException("numeroCuenta ya existe");
        }

        CuentaEntity e = new CuentaEntity();
        e.setNumeroCuenta(req.numeroCuenta());
        e.setTipoCuenta(req.tipoCuenta());
        e.setSaldoInicial(req.saldoInicial());
        e.setEstado(req.estado());
        e.setClienteId(req.clienteId());

        return cuentaRepository.save(e);
    }

    public CuentaEntity getById(Long id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("cuenta no encontrada"));
    }

    public List<CuentaEntity> list() {
        return cuentaRepository.findAll();
    }
}