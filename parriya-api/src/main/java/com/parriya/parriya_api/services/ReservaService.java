package com.parriya.parriya_api.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parriya.parriya_api.entidades.Reserva;
import com.parriya.parriya_api.entidades.dto.Reserva.ReservaRequest;
import com.parriya.parriya_api.entidades.dto.Reserva.ReservaDelDiaResponse;

import com.parriya.parriya_api.repository.ReservaRepository;

import jakarta.transaction.Transactional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    public Reserva crearReservaManual(ReservaRequest request) {
        Reserva reserva = new Reserva();
        reserva.setNombreCliente(request.getNombreCliente().toUpperCase());
        reserva.setTelefonoCliente(request.getTelefonoCliente());
        reserva.setCantidadDePersonas(request.getCantidadDePersonas());
        reserva.setFechaDeReserva(request.getFechaDeReserva());
        reserva.setHorarioDeReserva(request.getHorarioDeReserva());
        reserva.setUbicacion(request.getUbicacion().toUpperCase());
        
        // Estado inicial para una carga manual
        reserva.setEstado("CONFIRMADA"); 

        return reservaRepository.save(reserva);
    }

    //Traer todas las reservas
    public List<Reserva> obtenerTodas() {
        return reservaRepository.findAll();
    }

    //traer las reservas por nombre del cliente
    public List<Reserva> buscarPorNombre(String nombre) {
        return reservaRepository.findByNombreCliente(nombre);
    }

    //Cancelar Reserva
    @Transactional
    public Reserva cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        reserva.setEstado("CANCELADA");
        return reservaRepository.save(reserva);
    } 

    //Modificar Rererva
    @Transactional
    public Reserva actualizarReserva(Long id, ReservaRequest request) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        
        reserva.setNombreCliente(request.getNombreCliente());
        reserva.setTelefonoCliente(request.getTelefonoCliente());
        reserva.setCantidadDePersonas(request.getCantidadDePersonas());
        reserva.setFechaDeReserva(request.getFechaDeReserva());
        reserva.setHorarioDeReserva(request.getHorarioDeReserva());
        reserva.setUbicacion(request.getUbicacion().toUpperCase());
        
        return reservaRepository.save(reserva);
    }

    public ReservaDelDiaResponse obtenerReservasDivididasPorTurno(LocalDate fecha) {
List<Reserva> todasLasReservas = reservaRepository.findByFechaDeReserva(fecha);
        
        ReservaDelDiaResponse respuesta = new ReservaDelDiaResponse();
        LocalTime horaDeCorte = LocalTime.of(19, 0); 
        
        int sumaPersonas = 0;
        
        for (Reserva r : todasLasReservas) {
            sumaPersonas += r.getCantidadDePersonas();
            
            if (r.getHorarioDeReserva().isBefore(horaDeCorte)) {
                respuesta.getTurnoTarde().add(r);
            } else {
                respuesta.getTurnoNoche().add(r);
            }
        }
        
        // Seteamos los totales antes de devolver
        respuesta.setTotalReservas(todasLasReservas.size());
        respuesta.setTotalPersonas(sumaPersonas);
        
        return respuesta;
    }
}
