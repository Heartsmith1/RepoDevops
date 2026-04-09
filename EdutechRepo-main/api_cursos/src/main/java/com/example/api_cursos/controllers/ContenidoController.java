package com.example.api_cursos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.api_cursos.models.dto.ContenidoCrearDTO;
import com.example.api_cursos.models.entities.Contenido;
import com.example.api_cursos.models.request.ContenidoModificar;
import com.example.api_cursos.services.ContenidoService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/contenido")
public class ContenidoController {
    @Autowired
    private ContenidoService contenidoService;

    @Operation(summary = "Listar todos los contenidos", description = "Devuelve una lista de todos los contenidos disponibles.")
    @GetMapping("")
    public List<Contenido> listarTodos(){
        return contenidoService.obtenerTodos();
    }
    @Operation(summary = "Obtener un contenido por ID", description = "Devuelve los detalles de un contenido específico por su ID.")
    @PostMapping("")
    public ResponseEntity<?> crearNuevo(@RequestBody ContenidoCrearDTO dto) {
        try {
        Contenido nuevo = contenidoService.crearNuevo(dto);
        return ResponseEntity.ok(nuevo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear contenido: " + e.getMessage());
    }
}
    @Operation(summary = "Eliminar un contenido por ID", description = "Elimina un contenido existente por su ID.")
    @DeleteMapping("/{id}")
    public String eliminarContenido(@PathVariable int id){
        contenidoService.eliminarCont(id);
        return "contenido eliminado";
    }
    @Operation(summary = "Modificar un contenido", description = "Actualiza los datos de un contenido existente.")
    @PutMapping("")
    public Contenido modificar(@RequestBody ContenidoModificar body){
        return contenidoService.modificarCont(body);
    }
}
