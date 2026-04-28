package com.parriya.parriya_api.entidades.dto.Pago;

import java.util.Date;
import lombok.Data;

@Data
public class PagoResponse {
    private Long id;
    private String metodo;
    private double monto;
    private String moneda;
    private String estado;
    private Date fecha_pago;
}
