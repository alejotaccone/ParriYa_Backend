package com.parriya.parriya_api.entidades.dto.Feedback;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FeedbackResponse {
    private Long id;
    private String comentario;
    private Double calificacion;
    private LocalDateTime fecha;
    private String nombreCliente; // Sacado del usuario del pedido
    private Long pedidoId;
}