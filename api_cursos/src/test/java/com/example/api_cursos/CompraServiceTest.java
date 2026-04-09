package com.example.api_cursos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.api_cursos.models.entities.Compra;
import com.example.api_cursos.repositories.CompraRepository;
import com.example.api_cursos.services.CompraService;

@SpringBootTest
public class CompraServiceTest {
    @Autowired
    private CompraService compraService;

    @MockBean
    private CompraRepository compraRepository;

    @Test
    public void testGuardarCompra() {
        Compra compra = new Compra();
        compra.setId(1L);
        compra.setIdUsuario(100L);
        compra.setIdCurso(200L);

        when(compraRepository.save(compra)).thenReturn(compra);

        Compra resultado = compraService.guardarCompra(compra);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(100L, resultado.getIdUsuario());
        assertEquals(200L, resultado.getIdCurso());

        verify(compraRepository, times(1)).save(compra);
    }
}
