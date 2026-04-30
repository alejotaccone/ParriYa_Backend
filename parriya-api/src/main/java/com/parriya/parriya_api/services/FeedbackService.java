package com.parriya.parriya_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.parriya.parriya_api.entidades.Feedback;
import com.parriya.parriya_api.entidades.Pedido;
import com.parriya.parriya_api.entidades.Usuario;
import com.parriya.parriya_api.entidades.dto.Feedback.FeedbackRequest;
import com.parriya.parriya_api.entidades.dto.Feedback.FeedbackResponse;
import com.parriya.parriya_api.repository.FeedbackRepository;
import com.parriya.parriya_api.repository.PedidoRepository;
import com.parriya.parriya_api.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository; 

    @Transactional
    public FeedbackResponse registrarFeedback(FeedbackRequest request) {
        
        String emailAutenticado = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioAutenticado = usuarioRepository.findByEmail(emailAutenticado)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Pedido pedido = pedidoRepository.findById(request.getPedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        if (!pedido.getUsuario().getId().equals(usuarioAutenticado.getId())) {
            throw new RuntimeException("Acceso denegado: Solo el dueño del pedido puede dejar feedback");
        }

        if (!pedido.getEstado().equals("ENTREGADO")) {
            throw new RuntimeException("Solo podés dejar feedback de pedidos entregados");
        }

        if (feedbackRepository.existsByPedidoId(request.getPedidoId())) {
            throw new RuntimeException("Ya existe una reseña para este pedido");
        }

        Feedback feedback = new Feedback();
        feedback.setPedido(pedido);
        feedback.setComentario(request.getComentario());
        feedback.setCalificacion(request.getCalificacion());
        feedback.setFecha(LocalDateTime.now());

        Feedback savedFeedback = feedbackRepository.save(feedback);
        return mapearFeedbackResponse(savedFeedback);
    }

    private FeedbackResponse mapearFeedbackResponse(Feedback feedback) {
        FeedbackResponse dto = new FeedbackResponse();
        dto.setId(feedback.getId());
        dto.setComentario(feedback.getComentario());
        dto.setCalificacion(feedback.getCalificacion());
        dto.setFecha(feedback.getFecha());
        dto.setPedidoId(feedback.getPedido().getId());
        dto.setNombreCliente(feedback.getPedido().getUsuario().getNombre());
        return dto;
    }

    // Método para el Dashboard del Admin
    public List<FeedbackResponse> obtenerFeedbackReciente() {
        List<Feedback> feedbacks = feedbackRepository.findTop3ByOrderByFechaDesc();
        return mapearLista(feedbacks);
    }

    public List<FeedbackResponse> obtenerTodosLosFeedbacks() {
        List<Feedback> feedbacks = feedbackRepository.findAllByOrderByFechaDesc();
        return mapearLista(feedbacks);
    }

    // EL TRADUCTOR 
    private List<FeedbackResponse> mapearLista(List<Feedback> feedbacks) {
        List<FeedbackResponse> respuestas = new ArrayList<>();
        for (Feedback f : feedbacks) {
            FeedbackResponse dto = new FeedbackResponse();
            dto.setId(f.getId());
            dto.setComentario(f.getComentario());
            dto.setCalificacion(f.getCalificacion());
            dto.setFecha(f.getFecha());
            dto.setPedidoId(f.getPedido().getId());
            dto.setNombreCliente(f.getPedido().getUsuario().getNombre());
            
            respuestas.add(dto);
        }
        return respuestas;
    }
}
