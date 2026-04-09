package com.example.servicio_usuarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.servicio_usuarios.models.entities.Ticket;
import com.example.servicio_usuarios.repositories.TicketRepository;
import com.example.servicio_usuarios.services.TicketService;
@SpringBootTest
public class TicketServiceTest {

    // Inyecta una instancia real de TicketService
    @Autowired
    private TicketService ticketService;

    // Crea un mock del repositorio para simular su comportamiento en pruebas
    @MockBean
    private TicketRepository ticketRepository;

    @Test
    public void testCrearTicket() {
        // Datos de entrada simulados
        Long usuarioId = 1L;
        String asunto = "Asunto prueba";
        String descripcion = "Descripción prueba";

        // Crea un ticket simulado como el que se espera que retorne el repositorio
        Ticket ticketMock = new Ticket(usuarioId, asunto, descripcion, true);
        ticketMock.setId(1L); // Simula que ya tiene un ID asignado por la base de datos

        // Simula el comportamiento del repositorio al guardar el ticket
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticketMock);

        // Llama al método del servicio que se está probando
        Ticket resultado = ticketService.crearTicket(usuarioId, asunto, descripcion);

        // Verifica que el resultado no sea nulo
        assertNotNull(resultado);

        // Comprueba que los datos retornados coincidan con los simulados
        assertEquals(1L, usuarioId, resultado.getUsuarioId()); // Verifica ID de usuario
        assertEquals(asunto, resultado.getAsunto());           // Verifica asunto
        assertEquals(descripcion, resultado.getDescripcion()); // Verifica descripción
        assertTrue(resultado.isActivo());                      // Verifica que esté activo

        // Verifica que el método save del repositorio fue llamado exactamente una vez
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }
}
