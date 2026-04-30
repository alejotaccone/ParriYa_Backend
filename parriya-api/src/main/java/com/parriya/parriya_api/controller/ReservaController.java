package com.parriya.parriya_api.controller;

import com.parriya.parriya_api.entidades.Reserva;
import com.parriya.parriya_api.entidades.dto.Reserva.ReservaDashboardResponse;
import com.parriya.parriya_api.entidades.dto.Reserva.ReservaDelDiaResponse;
import com.parriya.parriya_api.entidades.dto.Reserva.ReservaRequest;
import com.parriya.parriya_api.services.ReservaService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    // Endpoint para obtener todas las reservas
    @Operation(
        summary = "Obtener todas las reservas",
        description = "Devuelve la lista de todas las reservas"
    )
    @GetMapping
    public ResponseEntity<List<Reserva>> verTodas() {
        return ResponseEntity.ok(reservaService.obtenerTodas());
    }

    // Endpoint para obtener todas las reservas por día: /reservas/dia/2026-05-10
    @Operation(
        summary = "Obtener reservas por día",
        description = "Devuelve la lista de reservas para un día específico"
    )
    @GetMapping("/dia/{fecha}")
    public ResponseEntity<ReservaDelDiaResponse> verPorDia(@PathVariable LocalDate fecha) {
        return ResponseEntity.ok(reservaService.obtenerReservasDivididasPorTurno(fecha));
    }
    
    // Endpoint para buscar reservas por nombre del cliente: /reservas/buscar?nombre=perez
    @Operation(
        summary = "Buscar reservas por nombre",
        description = "Devuelve la lista de reservas para un cliente específico"
    )
    @GetMapping("/buscar")
    public ResponseEntity<List<Reserva>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(reservaService.buscarPorNombre(nombre));
    }

    @Operation(
        summary = "Crear reserva",
        description = "Crea una nueva reserva"
    )
    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@RequestBody ReservaRequest request) {
        Reserva nuevaReserva = reservaService.crearReservaManual(request);
        return new ResponseEntity<>(nuevaReserva, HttpStatus.CREATED);
    }

    // Modificar
    @Operation(
        summary = "Modificar reserva",
        description = "Actualiza los datos de una reserva existente"
    )
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> modificar(@PathVariable Long id, @RequestBody ReservaRequest request) {
        return ResponseEntity.ok(reservaService.actualizarReserva(id, request));
    }

    // Cancelar
    @Operation(
        summary = "Cancelar reserva",
        description = "Cancela una reserva existente"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Reserva> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.cancelarReserva(id));
    }

    @Operation(
        summary = "Obtener reservas para el dashboard",
        description = "Devuelve la lista de reservas para el dashboard"
    )
    @GetMapping("/dashboard/hoy")
    public ResponseEntity<List<ReservaDashboardResponse>> obtenerReservasDashboard() {
        List<ReservaDashboardResponse> reservas = reservaService.obtenerProximasReservasDashboard();
        return ResponseEntity.ok(reservas);
    }
    
}
