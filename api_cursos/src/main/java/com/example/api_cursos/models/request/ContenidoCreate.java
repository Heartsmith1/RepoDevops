package com.example.api_cursos.models.request;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContenidoCreate {
    private int idCurso;
    @NotBlank
    private String titulo;
    @NotBlank
    private String descripcion;
    @NotBlank
    @URL
    private String urlVideo;
}
