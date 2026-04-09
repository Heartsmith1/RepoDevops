package com.example.servicio_usuarios.models.entities;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="usuario")
@Data
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true,nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    
    private String name;

    private String phone;

    @Column(name="fecha_creacion")
    private Date creationDate;

    @Column(nullable = false)
    private Boolean active;

    @ManyToAny(fetch = FetchType.EAGER)
    @JoinTable(
        name="usuario_rol",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name="rol_id")
    )
    private Set<UserRol> roles = new HashSet<>();
    
}
