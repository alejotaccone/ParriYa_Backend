package com.parriya.parriya_api.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> manejarExcepcion(RuntimeException ex) {
        Map<String, String> respuesta = new HashMap<>();
        
        respuesta.put("error", ex.getMessage());
        
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }
}
