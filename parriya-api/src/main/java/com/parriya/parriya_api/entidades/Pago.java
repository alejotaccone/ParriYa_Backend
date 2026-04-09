package com.parriya.parriya_api.entidades;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
public class Pago {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "pedido_id", nullable = false)
	private Pedido pedido;

	@Column(nullable = false)
	private String metodo;

	@Column(nullable = false)
	private double monto;

	@Column(nullable = false)
	private String moneda;

	@Column(nullable = false)
	private String estado;

	private Date fecha_pago;

	private String referencia;
}
