package com.example.api_cursos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.api_cursos.models.dto.ContenidoCrearDTO;
import com.example.api_cursos.models.entities.Contenido;
import com.example.api_cursos.models.entities.Curso;
import com.example.api_cursos.models.request.ContenidoModificar;
import com.example.api_cursos.repositories.ContenidoRepository;
import com.example.api_cursos.repositories.CursoRepository;

@Service
public class ContenidoService {
    @Autowired
    private ContenidoRepository contenidoRepository;
    @Autowired
    private CursoRepository cursoRepository;

    public List<Contenido> obtenerTodos(){
        return contenidoRepository.findAll();
    }

     public Contenido obtenerPorId(int id){
        Contenido cont = contenidoRepository.findById(id).orElse(null);
        if (cont==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Contenido no encontrado");
        }
        return cont;
    }

    public Contenido crearNuevo(ContenidoCrearDTO dto) {
    Curso curso = cursoRepository.findById(dto.getCursoId())
        .orElseThrow(() -> new RuntimeException("Curso no encontrado "));

    Contenido contenido = new Contenido();
    contenido.setTitulo(dto.getTitulo());
    contenido.setDescripcion(dto.getDescripcion());
    contenido.setUrlVideo(dto.getUrlVideo());
    contenido.setCurso(curso);

    return contenidoRepository.save(contenido);
    }

    public void eliminarCont(int id){
        Contenido contenido = obtenerPorId(id);
        contenidoRepository.delete(contenido);

    }

    public Contenido modificarCont(ContenidoModificar solicitud){
        Contenido contenido = obtenerPorId(solicitud.getId());
        if(solicitud.getTitulo() !=null){
            contenido.setTitulo(solicitud.getTitulo());
        }
        if(solicitud.getDescripcion() !=null){
            contenido.setDescripcion(solicitud.getDescripcion());
        }
        if(solicitud.getUrlVideo() !=null){
            contenido.setUrlVideo(solicitud.getUrlVideo());
        }
        return contenidoRepository.save(contenido);
    }
}
