package com.example.servicio_usuarios.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.servicio_usuarios.models.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    /*Metodo para buscar un usuario por su correo electronico. */
    User findByEmail(String email);

    User findByEmailAndActive(String email,Boolean active);

    List<User> findByActive(Boolean active);
}
