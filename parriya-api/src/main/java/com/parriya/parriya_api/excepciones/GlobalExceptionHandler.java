package com.parriya.parriya_api.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Este método está "escuchando" en todo el proyecto
    // Si algún Service hace un "throw new RuntimeException", cae acá directamente
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> manejarExcepcion(RuntimeException ex) {
        Map<String, String> respuesta = new HashMap<>();
        
        // Agarramos el mensaje exacto que escribiste en tu Service
        respuesta.put("error", ex.getMessage());
        
        // Devolvemos el JSON limpio con un código 400 (Bad Request)
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }
}
