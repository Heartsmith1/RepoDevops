package com.example.servicio_usuarios.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserCrear {
    
    @NotBlank
    @Email
    private String email;

    private String phone;

    @NotBlank
    private String password;

    private String name;

}
