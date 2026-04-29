package com.parriya.parriya_api.entidades.dto.Reserva;

import lombok.Data;
import java.time.LocalTime;

@Data
public class ReservaDashboardResponse {
    private LocalTime horario;
    private String nombreCliente;
    private int cantidadPersonas;
}
