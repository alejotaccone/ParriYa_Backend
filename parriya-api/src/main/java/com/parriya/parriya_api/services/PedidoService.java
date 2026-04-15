package com.parriya.parriya_api.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parriya.parriya_api.entidades.DetallePedido;
import com.parriya.parriya_api.entidades.Pago;
import com.parriya.parriya_api.entidades.Pedido;
import com.parriya.parriya_api.entidades.Producto;
import com.parriya.parriya_api.entidades.Usuario;
import com.parriya.parriya_api.entidades.dto.DetallePedido.DetallePedidoRequest;
import com.parriya.parriya_api.entidades.dto.DetallePedido.DetallePedidoResponse;
import com.parriya.parriya_api.entidades.dto.Pago.PagoResponse;
import com.parriya.parriya_api.entidades.dto.Pedido.PedidoRequest;
import com.parriya.parriya_api.entidades.dto.Pedido.PedidoResponse;

import com.parriya.parriya_api.repository.PedidoRepository;
import com.parriya.parriya_api.repository.ProductoRepository;
import com.parriya.parriya_api.repository.UsuarioRepository;

@Service
public class PedidoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public PedidoResponse crearPedido(PedidoRequest request) {
        // 1. Validar cliente
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Inicializar pedido
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setFecha_pedido(new Date());
        pedido.setHorario_retiro(request.getHorarioRetiro());
        pedido.setEstado("PENDIENTE");

        double totalPedido = 0;
        List<DetallePedido> detalles = new ArrayList<>();

        // 3. Procesar lista de productos
        for (DetallePedidoRequest item : request.getDetalles()) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            DetallePedido detalle = new DetallePedido();
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecio_unitario(producto.getPrecio()); 
            
            double subtotal = producto.getPrecio() * item.getCantidad();
            detalle.setSubtotal(subtotal);
            
            // Vinculación para el Cascade
            detalle.setPedido(pedido);

            totalPedido += subtotal;
            detalles.add(detalle);
        }

        // 4. Cerrar pedido y guardar
        pedido.setDetalles(detalles);
        pedido.setTotal(totalPedido);

        Pedido guardado = pedidoRepository.save(pedido);

        return mapearAResponse(guardado);
    }

    @Transactional
    public PedidoResponse cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));

        // Regla de negocio: solo se puede cancelar si el pedido recién entró
        if (!pedido.getEstado().equals("PENDIENTE")) {
            throw new RuntimeException("No se puede cancelar un pedido que se encuentra en estado: " + pedido.getEstado());
        }

        // Aplicamos el Soft Delete
        pedido.setEstado("CANCELADO");
        Pedido guardado = pedidoRepository.save(pedido);

        return mapearAResponse(guardado);
    }

    // --- TRADUCTOR A DTO ---
    private PedidoResponse mapearAResponse(Pedido pedido) {
        PedidoResponse response = new PedidoResponse();
        response.setId(pedido.getId());
        response.setUsuarioId(pedido.getUsuario().getId());
        response.setNombreUsuario(pedido.getUsuario().getNombre());
        response.setFechaPedido(pedido.getFecha_pedido()); // Cuidado acá: Date a Date
        response.setHorarioRetiro(pedido.getHorario_retiro());
        response.setEstado(pedido.getEstado());
        response.setTotal(pedido.getTotal());

        // Mapeo manual de Detalles
        List<DetallePedidoResponse> detallesDTO = new ArrayList<>();
        if (pedido.getDetalles() != null) {
            for (DetallePedido det : pedido.getDetalles()) {
                DetallePedidoResponse detDTO = new DetallePedidoResponse();
                detDTO.setId(det.getId());
                detDTO.setProductoId(det.getProducto().getId());
                detDTO.setCantidad(det.getCantidad());
                detDTO.setNombreProducto(det.getProducto().getNombre());
                detDTO.setPrecioUnitario(det.getPrecio_unitario());
                detDTO.setSubtotal(det.getSubtotal());
                detallesDTO.add(detDTO);
            }
        }
        response.setDetalles(detallesDTO);

        // Mapeo manual de Pagos
        List<PagoResponse> pagosDTO = new ArrayList<>();
        if (pedido.getPagos() != null) {
            for (Pago pago : pedido.getPagos()) {
                PagoResponse pRes = new PagoResponse();
                pRes.setId(pago.getId());
                pRes.setMetodo(pago.getMetodo());
                pRes.setMonto(pago.getMonto());
                pRes.setMoneda(pago.getMoneda());
                pRes.setEstado(pago.getEstado());
                pRes.setFecha_pago((java.sql.Date) pago.getFecha_pago());
                pagosDTO.add(pRes);
            }
        }
        response.setPagos(pagosDTO);

        return response;
    }
}
