package com.parriya.parriya_api.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parriya.parriya_api.entidades.Categoria;
import com.parriya.parriya_api.entidades.Producto;
import com.parriya.parriya_api.entidades.dto.Categoria.CategoriaResponse;
import com.parriya.parriya_api.entidades.dto.Producto.ProductoRequest;
import com.parriya.parriya_api.entidades.dto.Producto.ProductoResponse;
import com.parriya.parriya_api.repository.CategoriaRepository;
import com.parriya.parriya_api.repository.ProductoRepository;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    //Crear Producto
    public ProductoResponse createProducto(ProductoRequest request){
        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        Producto nuevoProducto = new Producto(
            request.getNombre(),
            request.getDescripcion(),
            request.getPrecio(),
            request.getImgUrl(),
            categoria);
        Producto guardada = productoRepository.save(nuevoProducto);
        return mapearAResponse(guardada);
    }

    //Obtener Producto por ID
    public Optional<ProductoResponse> getProductoPorId(Long id){
        return productoRepository.findById(id).map(this::mapearAResponse);
    }

    // Obtener todas las categorias
    public List<ProductoResponse> getProductos(){
        return productoRepository.findAll().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    //Eliminar
    public void deleteProducto(Long id){
        productoRepository.deleteById(id);
    }

    // Actualizar Producto recibiendo el DTO
    public ProductoResponse updateProducto(Long id, ProductoRequest request) {
        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setImgUrl(request.getImgUrl());
        producto.setCategoria(categoria);
        Producto actualizada = productoRepository.save(producto);
        
        return mapearAResponse(actualizada);
    }

    // Metodo de mapeo 
    private ProductoResponse mapearAResponse(Producto producto) {
        ProductoResponse response = new ProductoResponse();
        response.setId(producto.getId());
        response.setNombre(producto.getNombre());
        response.setDescripcion(producto.getDescripcion());
        response.setPrecio(producto.getPrecio());
        response.setEstado(true);
        response.setImgUrl(producto.getImgUrl());
        if (producto.getCategoria() != null) {
        response.setCategoriaId(producto.getCategoria().getId());
        }
        return response;
    }
}
