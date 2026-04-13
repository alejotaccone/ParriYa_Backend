package com.parriya.parriya_api.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parriya.parriya_api.entidades.Categoria;
import com.parriya.parriya_api.entidades.DetallePedido;
import com.parriya.parriya_api.entidades.Pedido;
import com.parriya.parriya_api.entidades.Producto;
import com.parriya.parriya_api.entidades.Usuario;
import com.parriya.parriya_api.entidades.dto.DetallePedido.DetallePedidoRequest;
import com.parriya.parriya_api.entidades.dto.Pedido.PedidoRequest;
import com.parriya.parriya_api.entidades.dto.Pedido.PedidoResponse;
import com.parriya.parriya_api.entidades.dto.Producto.ProductoRequest;
import com.parriya.parriya_api.entidades.dto.Producto.ProductoResponse;
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

    //Crear Producto
    public PedidoResponse createPedido(PedidoRequest request){
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setFecha_pedido(new Date());
        pedido.setHorario_retiro(request.getHorarioRetiro());
        pedido.setEstado("PENDIENTE");

        List<DetallePedido> detalles = new ArrayList<>();
        double totalPedido = 0;

        for (DetallePedidoRequest item : request.getDetalles()) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: ID " + item.getProductoId()));

            // Validación de stock
            if (producto.getStock() < item.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + producto.getNombre());
            }

            DetallePedido detalle = new DetallePedido();
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecio_unitario(producto.getPrecio()); 
            
            double subtotal = producto.getPrecio() * item.getCantidad();
            detalle.setSubtotal(subtotal);
            
            detalle.setPedido(pedido); // Clave para el Cascade

            totalPedido += subtotal;
            detalles.add(detalle);
        }

        pedido.setDetalles(detalles);
        pedido.setTotal(totalPedido);

        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        return mapearAResponse(pedidoGuardado);
    }

    // Metodo de mapeo 
    private PedidoResponse mapearAResponse(Pedido pedido) {
        PedidoResponse response = new PedidoResponse();
        response.setId(pedido.getId());
        response.setFechaPedido(pedido.getFecha_pedido());
        response.setHorarioRetiro(pedido.getHorario_retiro());
        response.setEstado("Pendiente");
        response.setTotal(pedido.getTotal());

        response.setUsuarioId(pedido.getUsuario().getId());
        response.setNombreUsuario(pedido.getUsuario().getNombre());
        response.setDetalles(pedido.getDetalles());
        response.setPagos(pedido.getPagos());
        return response;
    }
}
