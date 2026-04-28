package com.parriya.parriya_api.services;

import com.parriya.parriya_api.entidades.Pago;
import com.parriya.parriya_api.entidades.Pedido;
import com.parriya.parriya_api.entidades.dto.Pago.PagoRequest;
import com.parriya.parriya_api.entidades.dto.Pago.PagoResponse;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PagoService {

    public List<Pago> procesarPagos(List<PagoRequest> requests, double totalCalculado, Pedido pedido) {
        List<Pago> pagosProcesados = new ArrayList<>();
        double sumaPagos = 0;

        // 1. Recorrer la lista y armar los pagos
        for (PagoRequest req : requests) {
            Pago pago = new Pago();
            pago.setMetodo(req.getMetodo());
            pago.setMonto(req.getMonto());
            pago.setMoneda("ARS");
            pago.setEstado("PAGADO");
            pago.setFecha_pago(new Date()); // Usando java.util.Date
            pago.setPedido(pedido); // Fundamental para el Cascade

            sumaPagos += req.getMonto();
            pagosProcesados.add(pago);
        }

        // 2. Validar que la suma total de los pagos alcance para pagar la comida
        if (sumaPagos < totalCalculado) {
            throw new RuntimeException("El monto total ingresado ($" + sumaPagos + ") es insuficiente para cubrir el pedido de: $" + totalCalculado);
        }

        return pagosProcesados;
    }

    public PagoResponse mapearAResponse(Pago pago) {
    PagoResponse pRes = new PagoResponse();
    pRes.setId(pago.getId());
    pRes.setMetodo(pago.getMetodo());
    pRes.setMonto(pago.getMonto());
    pRes.setMoneda(pago.getMoneda());
    pRes.setEstado(pago.getEstado());
    pRes.setFecha_pago(pago.getFecha_pago());
    return pRes;
    }
}
