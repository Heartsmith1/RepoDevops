package com.example.api_cursos.services;

import com.example.api_cursos.models.entities.Compra;
import com.example.api_cursos.repositories.CompraRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ReporteService {
    @Autowired
    private CompraRepository compraRepository;

    public byte[] generarExcelCompras(List<Compra> compras) {
    try (Workbook workbook = new XSSFWorkbook()) {
        Sheet sheet = workbook.createSheet("Compras");

        // Encabezados
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Numero Compra");
        header.createCell(1).setCellValue("Nombre Curso");
        header.createCell(2).setCellValue("Email Usuario");
        header.createCell(3).setCellValue("Fecha"); 
        header.createCell(4).setCellValue("Precio");
        // Datos
        int rowNum = 1;
        for (Compra compra : compras) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(compra.getId());
            row.createCell(1).setCellValue(compra.getNombreCurso());
            row.createCell(2).setCellValue(compra.getEmailUsuario());
            row.createCell(3).setCellValue(compra.getFecha() != null ? compra.getFecha().toString() : "sin fecha"); 
            row.createCell(4).setCellValue(compra.getPrecio() != null ? compra.getPrecio() : 0.0);
            
        }

        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        return out.toByteArray();

    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}
public List<Compra> obtenerTodasLasCompras() {
        return compraRepository.findAll();
    }

}
