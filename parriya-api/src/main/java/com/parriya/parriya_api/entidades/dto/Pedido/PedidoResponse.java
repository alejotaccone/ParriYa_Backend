package com.parriya.parriya_api.entidades.dto.Pedido;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import com.parriya.parriya_api.entidades.DetallePedido;
import com.parriya.parriya_api.entidades.Pago;

import lombok.Data;

@Data
public class PedidoResponse {
    private Long id;
    private Long usuarioId;
    private String nombreUsuario;
    private Date fechaPedido;
    private LocalTime horarioRetiro;
    private String estado;
    private double total;
    private List<DetallePedido> detalles;
    private List<Pago> pagos;
}
