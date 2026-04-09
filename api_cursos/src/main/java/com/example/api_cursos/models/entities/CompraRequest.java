package com.example.api_cursos.models.entities;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CompraRequest {
    private int idCurso;

    private int idUsuario;
    

    @NotBlank
    private String numeroTarjeta;



}
