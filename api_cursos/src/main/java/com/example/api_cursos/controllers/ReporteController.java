package com.example.api_cursos.controllers;

import com.example.api_cursos.models.entities.Compra;
import com.example.api_cursos.services.ReporteService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReporteController {
     @Autowired
    private ReporteService reporteService;

    @Operation(summary = "Generar reporte de compras", description = "Genera un archivo Excel con todas las compras realizadas.")
    @GetMapping("/compras/reporte")
    public ResponseEntity<ByteArrayResource> generarReporteCompras() {
        try {
            List<Compra> compras = reporteService.obtenerTodasLasCompras();
        
            if (compras == null || compras.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            byte[] archivo = reporteService.generarExcelCompras(compras);

            ByteArrayResource resource = new ByteArrayResource(archivo);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_compras.xlsx")
                    .contentLength(archivo.length)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
}

}
