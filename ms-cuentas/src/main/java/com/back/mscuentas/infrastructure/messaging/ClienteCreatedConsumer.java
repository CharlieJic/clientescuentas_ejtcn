package com.back.mscuentas.infrastructure.messaging;

import com.back.mscuentas.infrastructure.messaging.event.ClienteCreatedEvent;
import com.back.mscuentas.infrastructure.persistence.entity.ClienteViewEntity;
import com.back.mscuentas.infrastructure.persistence.repository.ClienteViewRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

@Component
public class ClienteCreatedConsumer {

    private final ObjectMapper objectMapper;
    private final ClienteViewRepository clienteViewRepository;

    public ClienteCreatedConsumer(ObjectMapper objectMapper, ClienteViewRepository clienteViewRepository) {
        this.objectMapper = objectMapper;
        this.clienteViewRepository = clienteViewRepository;
    }

    @RabbitListener(queues = "cliente.created.queue")
    @Transactional
    public void onMessage(String messageJson) {
        try {
            ClienteCreatedEvent event = objectMapper.readValue(messageJson, ClienteCreatedEvent.class);

            ClienteViewEntity entity = new ClienteViewEntity();
            entity.setClienteId(event.clienteId());
            entity.setNombre(event.nombre());
            entity.setGenero(event.genero());
            entity.setEdad(event.edad());
            entity.setIdentificacion(event.identificacion());
            entity.setDireccion(event.direccion());
            entity.setTelefono(event.telefono());
            entity.setEstado(event.estado());

            // upsert: save sobrescribe si existe mismo clienteId
            clienteViewRepository.save(entity);

        } catch (Exception e) {
            throw new IllegalStateException("Error procesando cliente.created: " + messageJson, e);
        }
    }
}