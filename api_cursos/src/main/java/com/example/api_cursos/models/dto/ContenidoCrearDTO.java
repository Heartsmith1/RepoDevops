package com.example.api_cursos.models.dto;

import lombok.Data;

@Data
public class ContenidoCrearDTO {
    private String titulo;
    private String descripcion;
    private String urlVideo;
    private int cursoId;
}
