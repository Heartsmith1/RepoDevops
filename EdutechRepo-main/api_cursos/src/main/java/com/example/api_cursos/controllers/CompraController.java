package com.example.api_cursos.controllers;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.reactive.function.client.WebClient;

import com.example.api_cursos.models.entities.Compra;
import com.example.api_cursos.models.entities.CompraRequest;
import com.example.api_cursos.models.entities.CompraResponse;
import com.example.api_cursos.models.entities.Curso;
import com.example.api_cursos.models.entities.User;
import com.example.api_cursos.services.CompraService;
import com.example.api_cursos.services.CursoService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
@RestController
@RequestMapping("compra")
public class CompraController {
    @Autowired
    private CursoService cursoService;

    @Autowired
    private WebClient webClient;
    
    @Autowired
    private CompraService compraService;

    @Operation(summary = "Realizar una compra de curso", description = "Permite a un usuario comprar un curso especificado por su ID.")
    @PostMapping("/comprar")
    public CompraResponse comprar(@Valid @RequestBody CompraRequest compraRequest) {
        
        CompraResponse response = new CompraResponse();

        try {
            Curso cur = cursoService.obtenerPorId(compraRequest.getIdCurso());
            if(cur == null){throw new Exception("Curso no encontrado");}

            User usuario = webClient
                .get()
                .uri("http://localhost:8080/user/" + compraRequest.getIdUsuario())
                .retrieve()
                .bodyToMono(User.class)
                .block();

            if(usuario == null){throw new Exception("Usuario no encontrado");}

            // Crear entidad Compra
            Compra compra = new Compra();
            compra.setNombreCurso(cur.getNombre());
            compra.setEmailUsuario(usuario.getEmail());
            compra.setPrecio(cur.getPrecio());
            

            // Guardar en BD
            Compra compraGuardada = compraService.guardarCompra(compra);

            response.setIdBoleta("Compra exitosa curso: " + cur.getNombre() + " Correo usuario: " + usuario.getEmail());
            response.setExito(true);
        } catch (Exception e) {
            response.setExito(false);
            response.setMensaje("Error al procesar la compra: " + e.getMessage());
            return response;
        }
        
        return response;

    }
    }
   
