package com.parriya.parriya_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import com.parriya.parriya_api.entidades.Feedback;
import com.parriya.parriya_api.entidades.Pedido;
import com.parriya.parriya_api.entidades.dto.Feedback.FeedbackRequest;
import com.parriya.parriya_api.repository.FeedbackRepository;
import com.parriya.parriya_api.repository.PedidoRepository;

import jakarta.transaction.Transactional;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public Feedback registrarFeedback(FeedbackRequest request) {
        // 1. ¿Existe el pedido?
        Pedido pedido = pedidoRepository.findById(request.getPedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        // 2. ¿El pedido es del usuario que intenta opinar?
        if (!pedido.getUsuario().getId().equals(request.getUsuarioId())) {
            throw new RuntimeException("Solo el dueño del pedido puede dejar feedback");
        }

        // 3. ¿El pedido ya fue entregado?
        if (!pedido.getEstado().equals("ENTREGADO")) {
            throw new RuntimeException("Solo podés dejar feedback de pedidos entregados");
        }

        // 4. ¿Ya dejó feedback antes para este pedido?
        if (feedbackRepository.existsByPedidoId(request.getPedidoId())) {
            throw new RuntimeException("Ya existe una reseña para este pedido");
        }

        // Si pasó todo, guardamos
        Feedback feedback = new Feedback();
        feedback.setPedido(pedido);
        feedback.setComentario(request.getComentario());
        feedback.setCalificacion(request.getCalificacion());
        feedback.setFecha(LocalDateTime.now());

        return feedbackRepository.save(feedback);
    }

    // Método para el Dashboard del Admin
    public List<Feedback> obtenerFeedbackReciente() {
        return feedbackRepository.findTop3ByOrderByFechaDesc();
    }

    public List<Feedback> obtenerTodosLosFeedbacks() {
        return feedbackRepository.findAllByOrderByFechaDesc();
    }
}
