package com.parriya.parriya_api.entidades.dto.Reserva;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Data;

@Data
public class ReservaRequest {
    private String nombreCliente;
    private String telefonoCliente;
    private int cantidadDePersonas;
    private LocalDate fechaDeReserva;
    private LocalTime horarioDeReserva;
    private String ubicacion; // "ADENTRO" o "AFUERA"
}
