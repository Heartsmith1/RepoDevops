package com.example.api_cursos.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CursoCreate {
    @NotBlank
    private String nombre;
    private int precio;
}
