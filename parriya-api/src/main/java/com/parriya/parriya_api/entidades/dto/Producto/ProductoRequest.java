package com.parriya.parriya_api.entidades.dto.Producto;

import lombok.Data;

@Data
public class ProductoRequest {
    private String nombre;
    private String descripcion;
    private double precio;
    private String imgUrl;
    
    private Long categoriaId;
}
