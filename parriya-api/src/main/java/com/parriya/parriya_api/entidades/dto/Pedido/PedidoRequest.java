package com.parriya.parriya_api.entidades.dto.Pedido;

import java.time.LocalTime;
import java.util.List;

import com.parriya.parriya_api.entidades.DetallePedido;
import com.parriya.parriya_api.entidades.dto.DetallePedido.DetallePedidoRequest;

import lombok.Data;

@Data
public class PedidoRequest {
    private Long usuarioId;
    private LocalTime horarioRetiro;
    private double total;
    private List<DetallePedidoRequest> detalles;
}
