package com.back.mscuentas.api.controller;

import com.back.mscuentas.api.dto.ReporteResponse;
import com.back.mscuentas.application.service.ReporteService;
import com.back.mscuentas.domain.exception.BusinessException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteService service;

    public ReporteController(ReporteService service) {
        this.service = service;
    }

    @GetMapping
    public List<ReporteResponse> generar(
            @RequestParam @NotNull @Min(1) Long clienteId,

            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,

            @RequestParam(required = false) String fecha
    ) {
        if (fecha != null && !fecha.isBlank()) {
            LocalDate[] rango = parseRango(fecha);
            return service.generar(clienteId, rango[0], rango[1]);
        }

        if (fechaInicio == null || fechaFin == null) {
            throw new BusinessException("Debe enviar fecha=YYYY-MM-DD,YYYY-MM-DD o fechaInicio y fechaFin");
        }

        return service.generar(clienteId, fechaInicio, fechaFin);
    }

    private LocalDate[] parseRango(String fecha) {
        String sep = fecha.contains(",") ? "," : ":";
        String[] parts = fecha.split(sep);
        if (parts.length != 2) {
            throw new BusinessException("Formato inválido en fecha. Use YYYY-MM-DD,YYYY-MM-DD");
        }
        return new LocalDate[] { LocalDate.parse(parts[0].trim()), LocalDate.parse(parts[1].trim()) };
    }
}