package com.parriya.parriya_api.controller;

import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferencePayerRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PayController {

    @PostMapping("/crear-preferencia")
    public ResponseEntity<?> crearPreferencia(@RequestBody Map<String, Object> request) {
        try {
            String accessToken = "APP_USR-1392722681072236-061119-b869b27c8b5e1708bbea8f4dc10dc3e4-3466638035";

            // Configurar opciones de la solicitud inyectando el token maestro directamente
            MPRequestOptions requestOptions = MPRequestOptions.builder()
                    .accessToken(accessToken)
                    .build();

            // Extraer monto y titulo del request
            if (!request.containsKey("monto") || !request.containsKey("titulo")) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Faltan parámetros requeridos: 'monto' y 'titulo'.");
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }

            BigDecimal monto = new BigDecimal(request.get("monto").toString());
            String titulo = request.get("titulo").toString();

            // Construir el ítem de la preferencia
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .title(titulo)
                    .quantity(1)
                    .unitPrice(monto)
                    .currencyId("ARS")
                    .build();

            // Construir la preferencia de pago agregando el payer del entorno Sandbox
            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(Collections.singletonList(itemRequest))
                    .payer(PreferencePayerRequest.builder()
                            .email("TESTUSER4851217734402156556@testuser.com")
                            .build())
                    .build();

            // Crear el cliente y generar la preferencia inyectando las opciones
            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest, requestOptions);

            // Retornar exitosamente el init_point
            Map<String, String> response = new HashMap<>();
            response.put("init_point", preference.getInitPoint());
            response.put("sandbox_init_point", preference.getSandboxInitPoint());

            return ResponseEntity.ok(response);

        } catch (MPApiException e) {
            e.printStackTrace();
            String apiContent = e.getApiResponse() != null ? e.getApiResponse().getContent() : "No content";
            System.out.println("Mercado Pago API Error Content: " + apiContent);

            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error API Mercado Pago");
            error.put("content", apiContent);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        } catch (MPException e) {
            e.printStackTrace();
            System.out.println("Mercado Pago SDK Error: " + e.getMessage());

            Map<String, String> error = new HashMap<>();
            error.put("error", "Error SDK Mercado Pago: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error interno del servidor: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
