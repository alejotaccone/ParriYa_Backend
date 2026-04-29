package com.parriya.parriya_api.entidades.dto.Pedido;

import lombok.Data;

@Data
public class PedidoDashboardResponse {
    private Long id;
    private String nombreCliente;
    private String estado;
    private double precio;
}
