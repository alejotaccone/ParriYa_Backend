package com.parriya.parriya_api.entidades;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
public class Favoritos {
	@EmbeddedId
	private FavoritosId id;

	@ManyToOne
	@MapsId("usuario_id")
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@ManyToOne
	@MapsId("producto_id")
	@JoinColumn(name = "producto_id")
	private Producto producto;

	private Date fecha_agregado;
}

@Embeddable
class FavoritosId implements java.io.Serializable {
	private Long usuario_id;
	private Long producto_id;
}
