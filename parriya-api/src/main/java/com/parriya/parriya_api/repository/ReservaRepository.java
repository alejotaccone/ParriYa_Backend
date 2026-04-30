package com.parriya.parriya_api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parriya.parriya_api.entidades.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    
    List<Reserva> findByFechaDeReserva(LocalDate fecha);    
    List<Reserva> findByNombreCliente(String nombre);
    
    List<Reserva> findTop5ByFechaDeReservaAndEstadoOrderByHorarioDeReservaAsc(LocalDate fecha, String estado);} 