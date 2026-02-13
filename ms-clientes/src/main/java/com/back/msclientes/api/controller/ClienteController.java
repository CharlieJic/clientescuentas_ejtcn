package com.back.msclientes.api.controller;

import com.back.msclientes.api.dto.*;
import com.back.msclientes.application.service.ClienteService;
import com.back.msclientes.domain.model.Cliente;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse create(@Valid @RequestBody ClienteCreateRequest request) {
        return toResponse(service.create(request));
    }

    @GetMapping("/{id}")
    public ClienteResponse getById(@PathVariable Long id) {
        return toResponse(service.getById(id));
    }

    @GetMapping
    public List<ClienteResponse> list() {
        return service.list().stream().map(this::toResponse).toList();
    }

    @PutMapping("/{id}")
    public ClienteResponse update(@PathVariable Long id, @Valid @RequestBody ClienteUpdateRequest request) {
        return toResponse(service.update(id, request));
    }

    @PatchMapping("/{id}")
    public ClienteResponse patch(@PathVariable Long id, @Valid @RequestBody ClientePatchRequest request) {
        return toResponse(service.patch(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // Alternativa “más segura” que borrar: desactivar
    @PostMapping("/{id}/desactivar")
    public ClienteResponse deactivate(@PathVariable Long id) {
        return toResponse(service.deactivate(id));
    }

    private ClienteResponse toResponse(Cliente c) {
        return new ClienteResponse(
                c.getClienteId(),
                c.getNombre(),
                c.getGenero(),
                c.getEdad(),
                c.getIdentificacion(),
                c.getDireccion(),
                c.getTelefono(),
                c.getEstado()
        );
    }
}