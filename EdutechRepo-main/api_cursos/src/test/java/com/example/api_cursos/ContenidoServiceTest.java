package com.example.api_cursos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import com.example.api_cursos.models.entities.Contenido;
import com.example.api_cursos.repositories.ContenidoRepository;
import com.example.api_cursos.services.ContenidoService;

@SpringBootTest
public class ContenidoServiceTest {
    @Autowired
    private ContenidoService contenidoService;

    @MockBean
    private ContenidoRepository contenidoRepository;

    @Test
    public void testObtenerPorId_Existente() {
        Contenido cont = new Contenido();
        cont.setId(1);
        cont.setTitulo("Título ejemplo");

        when(contenidoRepository.findById(1)).thenReturn(Optional.of(cont));

        Contenido resultado = contenidoService.obtenerPorId(1);

        assertNotNull(resultado);
        assertEquals("Título ejemplo", resultado.getTitulo());
        verify(contenidoRepository, times(1)).findById(1);
    }

    @Test
    public void testObtenerPorId_NoExiste() {
        when(contenidoRepository.findById(2)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            contenidoService.obtenerPorId(2);
        });

        assertEquals("404 NOT_FOUND \"Contenido no encontrado\"", ex.getMessage());
        verify(contenidoRepository, times(1)).findById(2);
    }
}
