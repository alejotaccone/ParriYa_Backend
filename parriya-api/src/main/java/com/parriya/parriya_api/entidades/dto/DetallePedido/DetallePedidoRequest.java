package com.parriya.parriya_api.entidades.dto.DetallePedido;

import lombok.Data;

@Data
public class DetallePedidoRequest {
    private Long productoId;
    private int cantidad;
    private double precioUnitario;
}
