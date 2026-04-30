package com.parriya.parriya_api.repository;

import com.parriya.parriya_api.entidades.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    
    boolean existsByPedidoId(Long pedidoId);

    List<Feedback> findTop3ByOrderByFechaDesc();

    List<Feedback> findAllByOrderByFechaDesc();
}
