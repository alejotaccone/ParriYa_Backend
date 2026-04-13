package com.parriya.parriya_api.entidades.dto.DetallePedido;

import lombok.Data;

@Data
public class DetallePedidoResponse {
    private Long id;
    private Long productoId;
    private int cantidad;
    private String nombreProducto;
    private double precioUnitario;
    private double subtotal;
}
