package com.parriya.parriya_api.entidades;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long detallePedido_id;

    private int cantidad;
    private double precio_unitario;


    //Relaciones
    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    
}
