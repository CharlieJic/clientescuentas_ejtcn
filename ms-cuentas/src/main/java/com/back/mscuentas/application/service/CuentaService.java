package com.back.mscuentas.application.service;

import com.back.mscuentas.api.dto.CuentaCreateRequest;
import com.back.mscuentas.domain.exception.ConflictException;
import com.back.mscuentas.domain.exception.NotFoundException;
import com.back.mscuentas.infrastructure.persistence.entity.CuentaEntity;
import com.back.mscuentas.infrastructure.persistence.repository.ClienteViewRepository;
import com.back.mscuentas.infrastructure.persistence.repository.CuentaRepository;
import com.back.mscuentas.api.dto.CuentaPatchRequest;
import com.back.mscuentas.api.dto.CuentaUpdateRequest;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public CuentaEntity update(Long id, CuentaUpdateRequest req) {
        CuentaEntity e = cuentaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("cuenta no encontrada"));

        if (!clienteViewRepository.existsById(req.clienteId())) {
            throw new NotFoundException("cliente no existe en ms-cuentas (no ha llegado el evento)");
        }

        if (!e.getNumeroCuenta().equals(req.numeroCuenta())) {
            cuentaRepository.findByNumeroCuenta(req.numeroCuenta())
                    .filter(other -> !other.getCuentaId().equals(id))
                    .ifPresent(other -> { throw new ConflictException("numeroCuenta ya existe"); });
        }

        e.setNumeroCuenta(req.numeroCuenta());
        e.setTipoCuenta(req.tipoCuenta());
        e.setSaldoInicial(req.saldoInicial());
        e.setEstado(req.estado());
        e.setClienteId(req.clienteId());

        return cuentaRepository.save(e);
    }

    @Transactional
    public CuentaEntity patch(Long id, CuentaPatchRequest req) {
        CuentaEntity e = cuentaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("cuenta no encontrada"));

        if (req.clienteId() != null && !req.clienteId().equals(e.getClienteId())) {
            if (!clienteViewRepository.existsById(req.clienteId())) {
                throw new NotFoundException("cliente no existe en ms-cuentas (no ha llegado el evento)");
            }
            e.setClienteId(req.clienteId());
        }

        if (req.numeroCuenta() != null && !req.numeroCuenta().equals(e.getNumeroCuenta())) {
            cuentaRepository.findByNumeroCuenta(req.numeroCuenta())
                    .filter(other -> !other.getCuentaId().equals(id))
                    .ifPresent(other -> { throw new ConflictException("numeroCuenta ya existe"); });

            e.setNumeroCuenta(req.numeroCuenta());
        }

        if (req.tipoCuenta() != null) {
            e.setTipoCuenta(req.tipoCuenta());
        }

        if (req.saldoInicial() != null) {
            e.setSaldoInicial(req.saldoInicial());
        }

        if (req.estado() != null) {
            e.setEstado(req.estado());
        }

        return cuentaRepository.save(e);
    }

    /**
     * DELETE “senior-friendly”: desactiva la cuenta en vez de borrar físicamente.
     * Motivo: tienes historial de movimientos y un campo estado en la entidad Cuenta. :contentReference[oaicite:4]{index=4}
     */
    @Transactional
    public void delete(Long id) {
        CuentaEntity e = cuentaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("cuenta no encontrada"));

        e.setEstado(false);
        cuentaRepository.save(e);
    }
}