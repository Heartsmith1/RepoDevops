package com.example.servicio_usuarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.servicio_usuarios.models.entities.UserRol;

@Repository
public interface RolRepository extends  JpaRepository<UserRol,Integer> {

    UserRol findByNombre(String nombre);

}
