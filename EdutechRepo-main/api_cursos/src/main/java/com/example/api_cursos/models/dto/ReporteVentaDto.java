package com.example.api_cursos.models.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReporteVentaDto {

    private Long idCompra;
    private long idUsuario;
    private String nombreCurso;
    private LocalDate fecha;
    private Double monto;
}
