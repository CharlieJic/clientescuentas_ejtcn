package com.back.mscuentas.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cuenta", indexes = {
        @Index(name = "idx_cuenta_numero", columnList = "numero_cuenta", unique = true),
        @Index(name = "idx_cuenta_cliente", columnList = "cliente_id")
})
public class CuentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuenta_id")
    private Long cuentaId;

    @Column(name = "numero_cuenta", nullable = false, unique = true, length = 20)
    private String numeroCuenta;

    @Column(nullable = false, length = 30)
    private String tipoCuenta; // AHORROS / CORRIENTE (string para evitar acoplar)

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal saldoInicial;

    @Column(nullable = false)
    private Boolean estado;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    public CuentaEntity() {}

    public Long getCuentaId() { return cuentaId; }
    public void setCuentaId(Long cuentaId) { this.cuentaId = cuentaId; }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public String getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(String tipoCuenta) { this.tipoCuenta = tipoCuenta; }

    public BigDecimal getSaldoInicial() { return saldoInicial; }
    public void setSaldoInicial(BigDecimal saldoInicial) { this.saldoInicial = saldoInicial; }

    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
}