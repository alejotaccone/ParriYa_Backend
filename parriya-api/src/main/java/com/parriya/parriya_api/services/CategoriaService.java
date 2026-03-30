package com.parriya.parriya_api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parriya.parriya_api.entidades.Categoria;
import com.parriya.parriya_api.repository.CategoriaRepository;

@Service
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository categoriaRepository;

    //Crear Categoria
    public Categoria createCategoria(String nombre, String imgUrl){
        Categoria nuevaCategoria = new Categoria(nombre, imgUrl);
        return categoriaRepository.save(nuevaCategoria);
    }

    //Obtener Categoria por ID
    public Optional<Categoria> getCategoriaPorId(Long id){
        return categoriaRepository.findById(id);
    }

    public Iterable<Categoria> getCategorias(){
        return categoriaRepository.findAll();
    }
}
