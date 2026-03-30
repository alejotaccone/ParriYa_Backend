package com.parriya.parriya_api.entidades;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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



}
