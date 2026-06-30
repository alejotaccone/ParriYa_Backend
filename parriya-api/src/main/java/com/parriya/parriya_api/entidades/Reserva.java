package com.parriya.parriya_api.entidades;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
public class Reserva {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    private String nombreCliente;
	private String telefonoCliente;
	private int cantidadDePersonas;
	private LocalDate fechaDeReserva;
    private LocalTime horarioDeReserva;
	private String ubicacion;
	private String estado;
}
