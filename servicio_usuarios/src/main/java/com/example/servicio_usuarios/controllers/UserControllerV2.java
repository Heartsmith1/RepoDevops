package com.example.servicio_usuarios.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.servicio_usuarios.assemblers.UserModelAssembler;
import com.example.servicio_usuarios.models.entities.User;
import com.example.servicio_usuarios.models.request.UserCrear;
import com.example.servicio_usuarios.models.request.UserUpdate;
import com.example.servicio_usuarios.services.UserServices;

import io.swagger.v3.oas.annotations.Operation;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("v2/usuario")
public class UserControllerV2 {
    @Autowired
    private UserServices userService;

    @Autowired
    private UserModelAssembler assembler;
    

    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve una lista de todos los usuarios registrados.")
    @GetMapping("/")
    public CollectionModel<EntityModel<User>> obtenerTodos() {
        List<EntityModel<User>> usuarios = userService.obtenerTodos().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(usuarios,
            linkTo(methodOn(UserControllerV2.class).obtenerTodos()).withSelfRel());
    }
    @Operation(summary = "Obtener un usuario por ID", description = "Devuelve los detalles de un usuario específico por su ID.")
    @GetMapping("/{id}")
    public EntityModel<User> obtenerUno(@PathVariable int id) {
        User user = userService.obtenerPorId(id);
        return assembler.toModel(user);
    }
    @Operation(summary = "Registrar un nuevo usuario", description = "Crea un nuevo usuario con los datos proporcionados.")
    @PostMapping("/")
    public EntityModel<User> registrar(@Valid @RequestBody UserCrear body) {
        User user = userService.registrar(body);
        return assembler.toModel(user);
    }
    @Operation(summary = "Actualizar un usuario", description = "Actualiza los datos de un usuario existente.")
    @PutMapping("/")
    public EntityModel<User> actualizar(@Valid @RequestBody UserUpdate body) {
        User user = userService.modificar(body);
        return assembler.toModel(user);
    }

    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario existente por su ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
        userService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
