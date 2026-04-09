package com.example.api_cursos.models.request;

import lombok.Data;

@Data
public class CursoModificar {
    private int id;

    private String nombre;

    public int precio;
}
