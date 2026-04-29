package com.parriya.parriya_api.controller;

import com.parriya.parriya_api.entidades.dto.Pedido.PedidoDashboardResponse;
import com.parriya.parriya_api.entidades.dto.Pedido.PedidoRequest;
import com.parriya.parriya_api.entidades.dto.Pedido.PedidoResponse;
import com.parriya.parriya_api.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;


    // Endpoint para traer todos los pedidos ordenados
    @GetMapping
    public ResponseEntity<List<PedidoResponse>> obtenerTodos() {
        return new ResponseEntity<>(pedidoService.obtenerTodosLosPedidos(), HttpStatus.OK);
    }

    // 1. Crear un pedido nuevo (Acá entra el carrito con el pago)
    @PostMapping
    public ResponseEntity<PedidoResponse> crearPedido(@RequestBody PedidoRequest request) {
        PedidoResponse response = pedidoService.crearPedido(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 2. Traer un pedido específico por su ID (Para ver el ticket)
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> obtenerPedido(@PathVariable Long id) {
        PedidoResponse response = pedidoService.obtenerPedidoPorId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PedidoResponse>> obtenerMisPedidos(@PathVariable Long usuarioId) {
        List<PedidoResponse> respuestas = pedidoService.obtenerPedidosPorUsuario(usuarioId);
        return new ResponseEntity<>(respuestas, HttpStatus.OK);
    }

    // 3. Traer la lista de pedidos según su estado (Para la tablet de la cocina)
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PedidoResponse>> obtenerPedidosPorEstado(@PathVariable String estado) {
        List<PedidoResponse> respuestas = pedidoService.obtenerPedidosPorEstado(estado);
        return new ResponseEntity<>(respuestas, HttpStatus.OK);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<List<PedidoDashboardResponse>> obtenerUltimosPedidosDashboard() {
        List<PedidoDashboardResponse> respuestas = pedidoService.obtenerUltimosPedidos();
        return new ResponseEntity<>(respuestas, HttpStatus.OK);
    }

    // 4. Cancelar un pedido
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<PedidoResponse> cancelarPedido(@PathVariable Long id) {
        PedidoResponse response = pedidoService.cancelarPedido(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    //Confirmar entrega de un pedido
    @PutMapping("/{id}/entregar")
    public ResponseEntity<PedidoResponse> entregarPedido(@PathVariable Long id) {
        // Nota: Si implementaste el manejador global de errores, este código queda así de limpio
        PedidoResponse response = pedidoService.entregarPedido(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}