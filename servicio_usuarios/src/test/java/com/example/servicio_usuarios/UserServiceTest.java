package com.example.servicio_usuarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.servicio_usuarios.models.entities.User;
import com.example.servicio_usuarios.models.request.UserCrear;
import com.example.servicio_usuarios.repositories.UserRepository;
import com.example.servicio_usuarios.services.UserServices;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServices userServices;

    @Test
    void probarHasheoDePasswords(){

        String passAProbar = "Hola123";

        String hash = userServices.generarHash(passAProbar);

        Boolean coincide = userServices.comprobarPassword(hash, passAProbar);

        assertEquals(true, coincide);
    }

    @Test
    void probarAgregar(){

        String correo = "asd@asd.com";
        String nombre = "pruebita";

        UserCrear nuevoUser = new UserCrear();
        nuevoUser.setEmail(correo);
        nuevoUser.setPassword("123456");
        nuevoUser.setName(nombre);

        User userGuardado = new User();
        userGuardado.setEmail(correo);
        userGuardado.setName(nombre);

        when(userRepository.save(org.mockito.ArgumentMatchers.any(User.class)))
                .thenReturn(userGuardado);

        User usuarioEnBd = userServices.registrar(nuevoUser);

        assertEquals(correo, usuarioEnBd.getEmail());
        assertEquals(nombre, usuarioEnBd.getName());
    }
}