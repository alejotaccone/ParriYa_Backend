package com.parriya.parriya_api.repository;

import com.parriya.parriya_api.entidades.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    
    boolean existsByPedidoId(Long pedidoId);

    List<Feedback> findTop3ByOrderByFechaDesc();

    List<Feedback> findAllByOrderByFechaDesc();
}
