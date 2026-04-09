package com.example.servicio_usuarios.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.servicio_usuarios.models.entities.Ticket;
import com.example.servicio_usuarios.repositories.TicketRepository;

@Service
public class TicketService {
    
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket crearTicket(Long usuarioId, String asunto, String descripcion) {
        Ticket ticket = new Ticket(usuarioId, asunto, descripcion, true);
        return ticketRepository.save(ticket);
    }

    public List<Ticket> obtenerTicketsPorUsuario(Long usuarioId) {
        return ticketRepository.findByUsuarioId(usuarioId);
    }

    public Optional<Ticket> cambiarEstado(Long ticketId, boolean activo) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(ticketId);
        optionalTicket.ifPresent(ticket -> {
            ticket.setActivo(activo);
            ticketRepository.save(ticket);
        });
        return optionalTicket;
    }
}
