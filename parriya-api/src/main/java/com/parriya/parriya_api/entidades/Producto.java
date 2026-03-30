package com.parriya.parriya_api.entidades;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long producto_id;

    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private boolean estado;
    private String img_url;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
