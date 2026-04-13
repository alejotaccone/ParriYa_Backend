package com.parriya.parriya_api.entidades;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fecha_pedido;
    private String estado;
    private double total;
    private LocalTime horario_retiro;

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "pedido")
    private List<DetallePedido> detalles;

    @OneToMany(mappedBy = "pedido")
    private List<Pago> pagos;
}
