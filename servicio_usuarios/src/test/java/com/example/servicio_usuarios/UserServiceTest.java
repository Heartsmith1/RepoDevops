package com.example.servicio_usuarios;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.servicio_usuarios.models.entities.User;
import com.example.servicio_usuarios.models.request.UserCrear;
import com.example.servicio_usuarios.repositories.UserRepository;
import com.example.servicio_usuarios.services.UserServices;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserServices userServices;

    @Autowired
    private UserRepository userRepository;

    @Test
    void probarHasheoDePasswords(){

        String passAProbar = "Hola123";

        String hash = userServices.generarHash(passAProbar);

        Boolean coincide = userServices.comprobarPassword(hash, passAProbar);

        assertEquals(coincide, true);
    }

    @Test
    void probarAgregar(){
        String correo = "asd@asd.com";
        String nombre = "pruebita";
        User user = userRepository.findByEmail(correo);
        if (user != null){
            userRepository.delete(user);
        }
        UserCrear nuevoUser = new UserCrear();
        nuevoUser.setEmail(correo);
        nuevoUser.setPassword("123456");
        nuevoUser.setName(nombre);

        User usuarioEnBd = userServices.registrar(nuevoUser);
        assertEquals(usuarioEnBd.getEmail(), correo);
        assertEquals(usuarioEnBd.getName(), nombre);
    }
}
