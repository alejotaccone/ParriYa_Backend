package com.parriya.parriya_api.controller;

import com.parriya.parriya_api.entidades.dto.Pedido.PedidoDashboardResponse;
import com.parriya.parriya_api.entidades.dto.Pedido.PedidoRequest;
import com.parriya.parriya_api.entidades.dto.Pedido.PedidoResponse;
import com.parriya.parriya_api.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;


    // Endpoint para traer todos los pedidos ordenados
    @Operation(
        summary = "Obtener todos los pedidos",
        description = "Devuelve la lista de todos los pedidos"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<PedidoResponse>> obtenerTodos() {
        return new ResponseEntity<>(pedidoService.obtenerTodosLosPedidos(), HttpStatus.OK);
    }

    // Crear un pedido nuevo
    @Operation(
        summary = "Crear nuevo pedido",
        description = "Crea un nuevo pedido con los datos del carrito y el pago"
    )
    @PostMapping
    public ResponseEntity<PedidoResponse> crearPedido(@RequestBody PedidoRequest request) {
        PedidoResponse response = pedidoService.crearPedido(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Traer un pedido específico por su ID
    @Operation(
        summary = "Obtener pedido por ID",
        description = "Devuelve los datos de un pedido específico por su ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> obtenerPedido(@PathVariable Long id) {
        PedidoResponse response = pedidoService.obtenerPedidoPorId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Ya no pedimos el {usuarioId} en el path, es una ruta genérica
    @Operation(
        summary = "Obtener mis pedidos",
        description = "Devuelve la lista de pedidos del usuario logueado"
    )
    @GetMapping("/mis-pedidos")
    public ResponseEntity<List<PedidoResponse>> obtenerMisPedidos() {
        String emailAutenticado = SecurityContextHolder.getContext().getAuthentication().getName();
        
        List<PedidoResponse> respuestas = pedidoService.obtenerMisPedidos(emailAutenticado);
        return new ResponseEntity<>(respuestas, HttpStatus.OK);
    }

    // 3. Traer la lista de pedidos según su estado (Para la tablet de la cocina)
    @Operation(
        summary = "Obtener pedidos por estado",
        description = "Devuelve la lista de pedidos según su estado"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PedidoResponse>> obtenerPedidosPorEstado(@PathVariable String estado) {
        List<PedidoResponse> respuestas = pedidoService.obtenerPedidosPorEstado(estado);
        return new ResponseEntity<>(respuestas, HttpStatus.OK);
    }

    @Operation(
        summary = "Obtener los últimos pedidos para el dashboard",
        description = "Devuelve la lista de los últimos pedidos para mostrar en el dashboard del admin"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard")
    public ResponseEntity<List<PedidoDashboardResponse>> obtenerUltimosPedidosDashboard() {
        List<PedidoDashboardResponse> respuestas = pedidoService.obtenerUltimosPedidos();
        return new ResponseEntity<>(respuestas, HttpStatus.OK);
    }

    // 4. Cancelar un pedido
    @Operation(
        summary = "Cancelar pedido",
        description = "Cancela un pedido existente por su ID"
    )
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<PedidoResponse> cancelarPedido(@PathVariable Long id) {
        PedidoResponse response = pedidoService.cancelarPedido(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Confirmar entrega de un pedido
    @Operation(
        summary = "Confirmar entrega de pedido",
        description = "Confirma la entrega de un pedido existente por su ID"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/entregar")
    public ResponseEntity<PedidoResponse> entregarPedido(@PathVariable Long id) {
        // Nota: Si implementaste el manejador global de errores, este código queda así de limpio
        PedidoResponse response = pedidoService.entregarPedido(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
        summary = "Actualizar estado de pedido",
        description = "Actualiza el estado de un pedido existente (ADMIN)"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/estado")
    public ResponseEntity<PedidoResponse> actualizarEstado(@PathVariable Long id, @RequestParam String nuevoEstado) {
        PedidoResponse response = pedidoService.actualizarEstado(id, nuevoEstado);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}