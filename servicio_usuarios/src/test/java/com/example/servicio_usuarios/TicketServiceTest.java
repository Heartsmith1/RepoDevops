package com.example.servicio_usuarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.servicio_usuarios.models.entities.Ticket;
import com.example.servicio_usuarios.repositories.TicketRepository;
import com.example.servicio_usuarios.services.TicketService;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TicketRepository ticketRepository;

    @Test
    public void testCrearTicket() {

        Long usuarioId = 1L;
        String asunto = "Asunto prueba";
        String descripcion = "Descripción prueba";

        Ticket ticketMock = new Ticket(usuarioId, asunto, descripcion, true);
        ticketMock.setId(1L);

        when(ticketRepository.save(any(Ticket.class)))
                .thenReturn(ticketMock);

        Ticket resultado = ticketService.crearTicket(usuarioId, asunto, descripcion);

        assertNotNull(resultado);

        assertEquals(usuarioId, resultado.getUsuarioId());
        assertEquals(asunto, resultado.getAsunto());
        assertEquals(descripcion, resultado.getDescripcion());
        assertTrue(resultado.isActivo());

        verify(ticketRepository, times(1))
                .save(any(Ticket.class));
    }
}