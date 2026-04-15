package com.parriya.parriya_api.entidades.dto.Pedido;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import com.parriya.parriya_api.entidades.DetallePedido;
import com.parriya.parriya_api.entidades.Pago;
import com.parriya.parriya_api.entidades.dto.DetallePedido.DetallePedidoResponse;
import com.parriya.parriya_api.entidades.dto.Pago.PagoResponse;

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
    private List<DetallePedidoResponse> detalles;
    private List<PagoResponse> pagos;
}
