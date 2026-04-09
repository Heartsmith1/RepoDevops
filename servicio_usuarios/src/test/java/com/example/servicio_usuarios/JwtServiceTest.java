package com.example.servicio_usuarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.servicio_usuarios.models.entities.User;
import com.example.servicio_usuarios.services.JwtService;
@SpringBootTest
public class JwtServiceTest {
     @Autowired
    private JwtService jwtService;

    @Test
    public void testGenerarJwt() {
        // Arrange
        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");

        // Act
        String token = jwtService.generarJwt(user);

        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertEquals(3, token.split("\\.").length); // formato JWT: header.payload.signature
    }
}
