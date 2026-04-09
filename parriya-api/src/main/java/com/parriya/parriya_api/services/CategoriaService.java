package com.parriya.parriya_api.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parriya.parriya_api.entidades.Categoria;
import com.parriya.parriya_api.entidades.dto.Categoria.CategoriaRequest;
import com.parriya.parriya_api.entidades.dto.Categoria.CategoriaResponse;
import com.parriya.parriya_api.repository.CategoriaRepository;

@Service
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository categoriaRepository;

    // Crear Categoria recibiendo el DTO
    public CategoriaResponse createCategoria(CategoriaRequest request){
        Categoria nuevaCategoria = new Categoria(request.getNombre(), request.getImgUrl());
        Categoria guardada = categoriaRepository.save(nuevaCategoria);
        return mapearAResponse(guardada);
    }

    // Obtener Categoria por ID
    public Optional<CategoriaResponse> getCategoriaPorId(Long id){
        return categoriaRepository.findById(id).map(this::mapearAResponse);
    }

    // Obtener todas las categorias
    public List<CategoriaResponse> getCategorias(){
        return categoriaRepository.findAll().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    // Eliminar
    public void deleteCategoria(Long id){
        categoriaRepository.deleteById(id);
    }


    // Actualizar Categoria recibiendo el DTO
    public CategoriaResponse updateCategoria(Long id, CategoriaRequest request) throws Exception {
        Categoria categoriaActualizada = categoriaRepository.findById(id)
                .orElseThrow(() -> new Exception("Categoria no encontrada"));
        
        categoriaActualizada.setNombre(request.getNombre());
        categoriaActualizada.setImgUrl(request.getImgUrl());
        
        Categoria guardada = categoriaRepository.save(categoriaActualizada);
        return mapearAResponse(guardada);
    }

    // Metodo de mapeo interno
    private CategoriaResponse mapearAResponse(Categoria categoria) {
        CategoriaResponse response = new CategoriaResponse();
        response.setId(categoria.getId());
        response.setNombre(categoria.getNombre());
        response.setImgUrl(categoria.getImgUrl());
        return response;
    }
}
