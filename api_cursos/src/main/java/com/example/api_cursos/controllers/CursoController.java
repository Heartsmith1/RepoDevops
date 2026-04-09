package com.example.api_cursos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_cursos.models.entities.Curso;
import com.example.api_cursos.models.request.CursoCreate;
import com.example.api_cursos.models.request.CursoModificar;
import com.example.api_cursos.services.CursoService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RequestMapping("/curso")
@RestController
public class CursoController {
    @Autowired
    private CursoService cursoService;

    @Operation(summary = "Listar todos los cursos", description = "Devuelve una lista de todos los cursos disponibles.")
    @GetMapping("")
    public List<Curso> todos(){
        return cursoService.obtenerTodos();
    }
    @Operation(summary = "Obtener un curso por ID", description = "Devuelve los detalles de un curso específico por su ID.")
    @GetMapping("/{id}")
    public Curso listarUno(@PathVariable int id){
        return cursoService.obtenerPorId(id);

    }
    @Operation(summary = "Registrar un nuevo curso", description = "Crea un nuevo curso con los datos proporcionados.")
    @PostMapping("")
    public Curso cursoNuevo(@Valid @RequestBody CursoCreate cuerpo){
        return cursoService.crearNuevo(cuerpo);

    }
    @Operation(summary = "Eliminar un curso por ID", description = "Elimina un curso existente por su ID.")
    @DeleteMapping("/{id}")
    public String eliminarCurso(@PathVariable int id){
        cursoService.eliminarCurso(id);
        return "Curso eliminado";
    }
    @Operation(summary = "Modificar un curso", description = "Actualiza los datos de un curso existente.")
    @PutMapping("")
    public Curso modificar(@RequestBody CursoModificar body){
        return cursoService.modificarCurso(body);
    }
}
