package com.example.servicio_usuarios.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Entity
@Data
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;
    private String asunto;
    private String descripcion;
    private boolean activo;

    public Ticket(Long usuarioId, String asunto, String descripcion, boolean activo) {
        this.usuarioId = usuarioId;
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.activo = activo;
    }
    
}
