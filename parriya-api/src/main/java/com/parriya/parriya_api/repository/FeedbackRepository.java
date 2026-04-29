package com.parriya.parriya_api.repository;

import com.parriya.parriya_api.entidades.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    
    // Para validar que no exista otra reseña del mismo pedido
    boolean existsByPedidoId(Long pedidoId);

    // Para el Dashboard: Trae los últimos 3 feedbacks
    List<Feedback> findTop3ByOrderByFechaDesc();

    List<Feedback> findAllByOrderByFechaDesc();
}
