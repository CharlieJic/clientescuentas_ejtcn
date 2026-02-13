package com.back.mscuentas.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento", indexes = {
        @Index(name = "idx_mov_cuenta_fecha", columnList = "cuenta_id, fecha"),
        @Index(name = "idx_mov_fecha", columnList = "fecha")
})
public class MovimientoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movimiento_id")
    private Long movimientoId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cuenta_id", nullable = false)
    private CuentaEntity cuenta;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false, length = 15)
    private String tipoMovimiento; // DEPOSITO / RETIRO

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal valor; // siempre positivo (validación)

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal saldo; // saldo resultante

    public MovimientoEntity() {}

    public Long getMovimientoId() { return movimientoId; }
    public void setMovimientoId(Long movimientoId) { this.movimientoId = movimientoId; }

    public CuentaEntity getCuenta() { return cuenta; }
    public void setCuenta(CuentaEntity cuenta) { this.cuenta = cuenta; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getTipoMovimiento() { return tipoMovimiento; }
    public void setTipoMovimiento(String tipoMovimiento) { this.tipoMovimiento = tipoMovimiento; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }
}