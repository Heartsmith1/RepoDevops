package com.example.api_cursos.models.entities;






import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Table(name = "compra")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    

    private String nombreCurso;

    private String emailUsuario;

    private Double precio;

    private long idCurso;

    private long idUsuario;

    @Temporal(TemporalType.DATE)
    private Date fecha;



}
