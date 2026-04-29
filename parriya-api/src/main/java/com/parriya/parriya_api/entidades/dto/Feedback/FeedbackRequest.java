package com.parriya.parriya_api.entidades.dto.Feedback;

import lombok.Data;

@Data
public class FeedbackRequest {
    private Long pedidoId;
    private Long usuarioId;
    private String comentario;
    private Double calificacion;
}