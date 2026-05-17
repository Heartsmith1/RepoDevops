package com.example.servicio_usuarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.servicio_usuarios.models.entities.User;
import com.example.servicio_usuarios.services.JwtService;

public class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {

        jwtService = new JwtService();

        ReflectionTestUtils.setField(
            jwtService,
            "secret",
            "miclavesupersecreta123456789123456789123456"
        );
    }

    @Test
    public void testGenerarJwt() {

        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");

        String token = jwtService.generarJwt(user);

        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertEquals(3, token.split("\\.").length);
    }
}