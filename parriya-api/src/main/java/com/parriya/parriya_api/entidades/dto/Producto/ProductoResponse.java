package com.parriya.parriya_api.entidades.dto.Producto;

import lombok.Data;

@Data
public class ProductoResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;
    private boolean estado;
    private String imgUrl;
    private Long categoriaId;
}
