package com.parriya.parriya_api.controller;

import com.parriya.parriya_api.entidades.Reserva;
import com.parriya.parriya_api.entidades.dto.Reserva.ReservaDelDiaResponse;
import com.parriya.parriya_api.entidades.dto.Reserva.ReservaRequest;
import com.parriya.parriya_api.services.ReservaService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    // Endpoint para obtener todas las reservas
    @GetMapping
    public ResponseEntity<List<Reserva>> verTodas() {
        return ResponseEntity.ok(reservaService.obtenerTodas());
    }

    // Endpoint para obtener todas las reservas por día: /reservas/dia/2026-05-10
    @GetMapping("/dia/{fecha}")
    public ResponseEntity<ReservaDelDiaResponse> verPorDia(@PathVariable LocalDate fecha) {
        return ResponseEntity.ok(reservaService.obtenerReservasDivididasPorTurno(fecha));
    }
    
    // Endpoint para buscar reservas por nombre del cliente: /reservas/buscar?nombre=perez
    @GetMapping("/buscar")
    public ResponseEntity<List<Reserva>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(reservaService.buscarPorNombre(nombre));
    }

    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@RequestBody ReservaRequest request) {
        Reserva nuevaReserva = reservaService.crearReservaManual(request);
        return new ResponseEntity<>(nuevaReserva, HttpStatus.CREATED);
    }

    // Modificar
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> modificar(@PathVariable Long id, @RequestBody ReservaRequest request) {
        return ResponseEntity.ok(reservaService.actualizarReserva(id, request));
    }

    // Cancelar
    @DeleteMapping("/{id}")
    public ResponseEntity<Reserva> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.cancelarReserva(id));
    }

    
}
