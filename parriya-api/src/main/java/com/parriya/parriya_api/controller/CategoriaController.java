package com.parriya.parriya_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parriya.parriya_api.entidades.Categoria;
import com.parriya.parriya_api.services.CategoriaService;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("categorias")
public class CategoriaController {
    
     // Inyección de dependencia del servicio de categoría
    @Autowired
    private CategoriaService categoriaService;

    // Endpoint para obtener todas las categorias
    @GetMapping
    public ResponseEntity<Iterable<Categoria>> getCategorias(){
        return ResponseEntity.ok(categoriaService.getCategorias());
    }

    // Endpoint para obtener una categorias por ID
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoriaPorId(@PathVariable Long id){
        return categoriaService.getCategoriaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }   
    
    // Endpoint para crear una categorias
    @PostMapping("/crearCategoria")
    public ResponseEntity<Object> createCategoria(@RequestBody Categoria categoriaRequest) {
        try {
            Categoria categoria = categoriaService.createCategoria(categoriaRequest.getNombre(), categoriaRequest.getImgUrl());
            return ResponseEntity.ok(categoria);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al procesar la solicitud: " + e.getMessage());
        }
        
    }
    


}
