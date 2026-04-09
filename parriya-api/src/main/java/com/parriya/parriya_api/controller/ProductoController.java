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

import com.parriya.parriya_api.entidades.dto.Categoria.CategoriaResponse;
import com.parriya.parriya_api.entidades.dto.Producto.ProductoRequest;
import com.parriya.parriya_api.entidades.dto.Producto.ProductoResponse;
import com.parriya.parriya_api.services.ProductoService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/productos")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;

    // Endpoint para obtener todas las categorias
    @GetMapping
    public ResponseEntity<List<ProductoResponse>> getProductos() {
        return ResponseEntity.ok(productoService.getProductos());
    }

    // Endpoint para obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> getProductoPorId(@PathVariable Long id){
        return productoService.getProductoPorId(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para crear un producto
    @PostMapping("/prodcutos")
    public ResponseEntity<ProductoResponse> createProducto(@RequestBody ProductoRequest productoRequest){
        ProductoResponse response = productoService.createProducto(productoRequest);
        return ResponseEntity.ok(response);
    }

    // Endpoint para actualizar un producto
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> updateProducto(@PathVariable Long id, @RequestBody ProductoRequest productoRequest) {
        try {
            ProductoResponse updated = productoService.updateProducto(id, productoRequest);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Endpoint para eliminar un producto por su ID   
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProducto(@PathVariable Long id) {
        Optional<ProductoResponse> producto = productoService.getProductoPorId(id);
        if (producto.isPresent()) {
            productoService.deleteProducto(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }


}
