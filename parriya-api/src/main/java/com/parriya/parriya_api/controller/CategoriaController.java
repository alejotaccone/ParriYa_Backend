package com.parriya.parriya_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parriya.parriya_api.entidades.dto.Categoria.CategoriaRequest;
import com.parriya.parriya_api.entidades.dto.Categoria.CategoriaResponse;
import com.parriya.parriya_api.services.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    
    @Autowired
    private CategoriaService categoriaService;

    // Endpoint para obtener todas las categorias
    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> getCategorias(){
        return ResponseEntity.ok(categoriaService.getCategorias());
    }

    // Endpoint para obtener una categorias por ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> getCategoriaPorId(@PathVariable Long id){
        return categoriaService.getCategoriaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }   
    
    // Endpoint para crear una categorias
    @PostMapping
    public ResponseEntity<CategoriaResponse> createCategoria(@RequestBody CategoriaRequest categoriaRequest) {
        CategoriaResponse response = categoriaService.createCategoria(categoriaRequest);
        return ResponseEntity.ok(response);
    }

    // Endpoint para actualizar una categoría 
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCategoria(
            @PathVariable Long id,
            @RequestBody CategoriaRequest categoriaRequest) {
        try {
            CategoriaResponse updated = categoriaService.updateCategoria(id, categoriaRequest);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint para eliminar una categoría por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategoria(@PathVariable Long id) {
        Optional<CategoriaResponse> categoria = categoriaService.getCategoriaPorId(id);
        if (categoria.isPresent()) {
            categoriaService.deleteCategoria(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}
