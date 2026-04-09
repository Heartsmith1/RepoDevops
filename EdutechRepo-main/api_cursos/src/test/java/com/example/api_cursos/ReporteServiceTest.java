package com.example.api_cursos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.api_cursos.models.entities.Compra;
import com.example.api_cursos.repositories.CompraRepository;
import com.example.api_cursos.services.ReporteService;

@SpringBootTest
public class ReporteServiceTest {
    
    @Autowired
    private ReporteService reporteService;

    @MockBean
    private CompraRepository compraRepository;

    @Test
    public void testGenerarExcelCompras() throws Exception {
        Compra compra1 = new Compra();
        compra1.setId(1L);
        compra1.setNombreCurso("Curso 1");
        compra1.setEmailUsuario("usuario1@example.com");
        compra1.setFecha(new Date());
        compra1.setPrecio(100.0);

        Compra compra2 = new Compra();
        compra2.setId(2L);
        compra2.setNombreCurso("Curso 2");
        compra2.setEmailUsuario("usuario2@example.com");
        compra2.setFecha(new Date());
        compra2.setPrecio(200.0);

        List<Compra> compras = List.of(compra1, compra2);

        byte[] excelBytes = reporteService.generarExcelCompras(compras);

        assertNotNull(excelBytes);
        assertTrue(excelBytes.length > 0);

        try (var inputStream = new ByteArrayInputStream(excelBytes);
             var workbook = WorkbookFactory.create(inputStream)) {

            var sheet = workbook.getSheet("Compras");
            assertNotNull(sheet);

            var headerRow = sheet.getRow(0);
            assertEquals("Numero Compra", headerRow.getCell(0).getStringCellValue());
            assertEquals("Nombre Curso", headerRow.getCell(1).getStringCellValue());
            assertEquals("Email Usuario", headerRow.getCell(2).getStringCellValue());
            assertEquals("Fecha", headerRow.getCell(3).getStringCellValue());
            assertEquals("Precio", headerRow.getCell(4).getStringCellValue());

            var row1 = sheet.getRow(1);
            assertEquals(1, (long) row1.getCell(0).getNumericCellValue());
            assertEquals("Curso 1", row1.getCell(1).getStringCellValue());
            assertEquals("usuario1@example.com", row1.getCell(2).getStringCellValue());
            assertEquals(100.0, row1.getCell(4).getNumericCellValue());

            var row2 = sheet.getRow(2);
            assertEquals(2, (long) row2.getCell(0).getNumericCellValue());
            assertEquals("Curso 2", row2.getCell(1).getStringCellValue());
            assertEquals("usuario2@example.com", row2.getCell(2).getStringCellValue());
            assertEquals(200.0, row2.getCell(4).getNumericCellValue());
        }
    }
}
