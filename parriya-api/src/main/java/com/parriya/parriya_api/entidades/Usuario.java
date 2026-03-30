package com.parriya.parriya_api.entidades;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long usuario_id;

	@Column(nullable = false)
	private String nombre;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password_hash;

	private String telefono;

	private Date fecha_registro;

	private String rol;

	// Relaciones
	@OneToMany(mappedBy = "usuario")
	private List<Reserva> reservas;

	@OneToMany(mappedBy = "usuario")
	private List<Favoritos> favoritos;
}
