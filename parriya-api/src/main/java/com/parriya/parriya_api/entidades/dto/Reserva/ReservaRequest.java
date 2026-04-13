package com.parriya.parriya_api.entidades.dto.Reserva;

import java.util.Date;
import lombok.Data;

@Data
public class ReservaRequest {
    private Long usuarioId;
    private int cantidadPersonas;
    private Date fechaReserva;
}
