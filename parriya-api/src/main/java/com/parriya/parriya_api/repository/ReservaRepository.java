package com.parriya.parriya_api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parriya.parriya_api.entidades.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    
    // Busca todas las reservas de un día específico
    List<Reserva> findByFechaDeReserva(LocalDate fecha);    
    // Busca por nombre (usamos Containing para que busque coincidencias parciales)
    List<Reserva> findByNombreCliente(String nombre);
    
} 