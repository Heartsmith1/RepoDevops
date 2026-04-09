package com.example.servicio_usuarios.models.request;



import lombok.Data;

@Data
public class UserUpdate {
    
    private int id;

    private String phone;

    private String password;

    private String name;

}
