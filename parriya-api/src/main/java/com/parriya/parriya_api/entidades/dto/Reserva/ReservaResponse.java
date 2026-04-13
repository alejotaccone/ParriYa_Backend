package com.parriya.parriya_api.entidades.dto.Reserva;

import java.util.Date;

import com.parriya.parriya_api.entidades.Usuario;

import lombok.Data;

@Data
public class ReservaResponse {
    private Long id;
	private Usuario usuario;
	private int cant_personas;
	private Date fechaReserva;
	private Date fechaExpiracion;
	private String estado;
}
