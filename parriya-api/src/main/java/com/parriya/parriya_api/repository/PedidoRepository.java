package com.parriya.parriya_api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.parriya.parriya_api.entidades.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
        List<Pedido> findAllByOrderByIdDesc();
        List<Pedido> findByEstado(String estado);
        List<Pedido> findByUsuarioIdOrderByIdDesc(Long usuarioId);
        List<Pedido> findTop5ByEstadoNotOrderByIdDesc(String estado);
}