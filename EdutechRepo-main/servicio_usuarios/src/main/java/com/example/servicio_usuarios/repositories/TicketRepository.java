package com.example.servicio_usuarios.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.servicio_usuarios.models.entities.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUsuarioId(Long userId);
}
