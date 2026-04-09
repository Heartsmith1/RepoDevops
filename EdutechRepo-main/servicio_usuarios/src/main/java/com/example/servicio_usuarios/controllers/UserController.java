package com.example.servicio_usuarios.controllers;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.servicio_usuarios.models.entities.User;
import com.example.servicio_usuarios.models.request.UserCrear;
import com.example.servicio_usuarios.models.request.UserUpdate;
import com.example.servicio_usuarios.services.UserServices;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("user")
public class UserController {
    
    @Autowired
    private UserServices userServices;

    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve una lista de todos los usuarios registrados.")
    @GetMapping("/")
    public List<User> traerTodos(){
        return userServices.obtenerTodos();
    }
    
    @Operation(summary = "Obtener un usuario por ID", description = "Devuelve los detalles de un usuario específico por su ID.")
     @GetMapping("/{id}")
    public User listarUno(@PathVariable int id){
        return userServices.obtenerPorId(id);

    }
    @Operation(summary = "Registrar un nuevo usuario", description = "Crea un nuevo usuario con los datos proporcionados.")
    @PostMapping("/")
    public User crear(@Valid@RequestBody UserCrear nuevo){
        return userServices.registrar(nuevo);
    }
    @Operation(summary = "Actualizar un usuario", description = "Actualiza los datos de un usuario existente.")
    @PutMapping("/")
    public User modificar(@Valid @RequestBody UserUpdate body){
        return userServices.modificar(body);
    }
    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario existente por su ID.")
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable int id) {
        userServices.eliminar(id);
        return "ok";
    }
    @Operation(summary = "Asignar un rol a un usuario", description = "Asigna un rol específico a un usuario por su ID.")
    @PostMapping("/{id}/roles")
    public ResponseEntity<?> asignarRol(@PathVariable int id, @RequestParam String rol){
        userServices.asignarRolAUsuario(id, rol);
        return ResponseEntity.ok("Rol asignado");
    }

    
}
