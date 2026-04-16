package com.parriya.parriya_api.entidades.dto.Pago;

import lombok.Data;

@Data
public class PagoRequest {
    private String metodo; 
    private double monto;  
}