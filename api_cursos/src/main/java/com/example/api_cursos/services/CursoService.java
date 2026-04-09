package com.example.api_cursos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.api_cursos.models.entities.Curso;
import com.example.api_cursos.models.request.CursoCreate;
import com.example.api_cursos.models.request.CursoModificar;
import com.example.api_cursos.repositories.CursoRepository;

@Service
public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;

    public Curso obtenerPorId(int id){
        Curso curso = cursoRepository.findById(id).orElse(null);
        if (curso==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Curso no encontrado");
        }
        return curso;
    }
    public List<Curso> obtenerTodos(){
        return cursoRepository.findAll();
    }
    
    public Curso crearNuevo(CursoCreate solicitud){
        Curso nuevo = new Curso();
        nuevo.setNombre(solicitud.getNombre());
        nuevo.setPrecio(solicitud.getPrecio());
        return cursoRepository.save(nuevo);
    }

    public void eliminarCurso(int id){
        Curso curso = obtenerPorId(id);
        cursoRepository.delete(curso);
    }

    public Curso modificarCurso(CursoModificar solicitud){
        Curso curso = obtenerPorId(solicitud.getId());
        if(solicitud.getNombre()!=null){
            curso.setNombre(solicitud.getNombre());
        }
        if(solicitud.getPrecio()>0){
            curso.setPrecio(solicitud.getPrecio());
        }
        return cursoRepository.save(curso);
    }


}
