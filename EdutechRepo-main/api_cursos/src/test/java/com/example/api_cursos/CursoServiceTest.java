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

import com.example.api_cursos.models.entities.Curso;
import com.example.api_cursos.repositories.CursoRepository;
import com.example.api_cursos.services.CursoService;

@SpringBootTest
public class CursoServiceTest {

    @Autowired
    private CursoService cursoService;

    @MockBean
    private CursoRepository cursoRepository;

    @Test
    public void testObtenerPorId_Existente() {
        Curso curso = new Curso();
        curso.setId(1);
        curso.setNombre("Curso Test");
        curso.setPrecio(1000);

        when(cursoRepository.findById(1)).thenReturn(Optional.of(curso));

        Curso resultado = cursoService.obtenerPorId(1);

        assertNotNull(resultado);
        assertEquals("Curso Test", resultado.getNombre());
        verify(cursoRepository, times(1)).findById(1);
    }
    @Test
    public void testObtenerPorId_NoExiste() {
        when(cursoRepository.findById(2)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            cursoService.obtenerPorId(2);
        });

        assertEquals("404 NOT_FOUND \"Curso no encontrado\"", ex.getMessage());
        verify(cursoRepository, times(1)).findById(2);
    }
}
