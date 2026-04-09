package com.example.servicio_usuarios.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.servicio_usuarios.models.entities.User;
import com.example.servicio_usuarios.models.entities.UserRol;
import com.example.servicio_usuarios.models.request.UserCrear;
import com.example.servicio_usuarios.models.request.UserUpdate;
import com.example.servicio_usuarios.repositories.RolRepository;
import com.example.servicio_usuarios.repositories.UserRepository;

@Service
public class UserServices {
    
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RolRepository rolRepo;

    @Autowired 
    JwtService jwtService;

    // Obtiene todos los usuarios registrados
    public List<User> obtenerTodos() {
        return userRepo.findAll();
    }

    // Obtiene solo los usuarios activos (campo active en true)
    public List<User> obtenerActivos() {
        return userRepo.findByActive(true);
    }

    // Busca un usuario por su ID, lanza excepción si no existe
    public User obtenerPorId(int id) {
        User usuario = userRepo.findById(id).orElse(null);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        return usuario;
    }

    // Busca un usuario por su email
    public User obtenerPorEmail(String email) {
        User usuario = userRepo.findByEmail(email);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return usuario;
    }

    // Registra un nuevo usuario a partir del DTO UserCrear
    public User registrar(UserCrear usuario) {
        try {
            User nuevoUser = new User();
            nuevoUser.setActive(true); // Marca el usuario como activo
            nuevoUser.setCreationDate(new Date()); // Fecha de creación
            nuevoUser.setEmail(usuario.getEmail());
            nuevoUser.setName(usuario.getName());
            nuevoUser.setPassword(generarHash(usuario.getPassword())); // Encripta contraseña
            nuevoUser.setPhone(usuario.getPhone());

            return userRepo.save(nuevoUser); // Guarda en base de datos
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario Registrado");
        }
    }

    // Modifica los datos de un usuario existente
    public User modificar(UserUpdate modificado) {
        User usuario = userRepo.findById(modificado.getId()).orElse(null);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no encontrado");
        }

        if (modificado.getName() != null) {
            usuario.setName(modificado.getName());
        }

        if (modificado.getPhone() != null) {
            usuario.setPhone(modificado.getPhone()); 
        }

        if (modificado.getPassword() != null) {
            usuario.setPassword(generarHash(modificado.getPassword())); // Encripta nueva contraseña
        }

        return userRepo.save(usuario); // Guarda los cambios
    }

    // Genera un hash BCrypt a partir de la contraseña en texto plano
    public String generarHash(String password) {
        PasswordEncoder hasheador = new BCryptPasswordEncoder();
        return hasheador.encode(password);
    }

    // Elimina un usuario por ID, lanza error si no existe
    public void eliminar(int id) {
        User usuario = userRepo.findById(id).orElse(null);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        userRepo.delete(usuario);
    }

    // Asigna un rol existente a un usuario
    public void asignarRolAUsuario(int id, String nombreRol) {
        User usuario = userRepo.findById(id).orElse(null);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }

        UserRol rol = rolRepo.findByNombre(nombreRol);
        if (rol == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado");
        }

        usuario.getRoles().add(rol); // Agrega el rol a la colección
        userRepo.save(usuario); // Guarda los cambios
    }

    // Compara una contraseña en texto plano con su versión encriptada
    public boolean comprobarPassword(String hash, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, hash); // Retorna true si coinciden
    }

    // Intenta iniciar sesión validando correo y contraseña
    public String intentarLogin(String email, String password) {
        User user = obtenerPorEmail(email);

        if (user != null) {
            boolean passwordCorrecta = comprobarPassword(user.getPassword(), password);

            if (passwordCorrecta) {
                return jwtService.generarJwt(user); // Genera y retorna el token JWT
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contraseña invalida");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Correo no registrado");
        }
    }
}
