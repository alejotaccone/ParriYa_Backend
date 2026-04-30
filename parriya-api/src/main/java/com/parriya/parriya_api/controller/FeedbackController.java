package com.parriya.parriya_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parriya.parriya_api.entidades.dto.Feedback.FeedbackRequest;
import com.parriya.parriya_api.entidades.dto.Feedback.FeedbackResponse;
import com.parriya.parriya_api.services.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Operation(
        summary = "Crear feedback",
        description = "Crea un nuevo feedback con los datos proporcionados"
    )
    @PostMapping
    public ResponseEntity<Object> crearFeedback(@RequestBody FeedbackRequest request) {
        try {
            FeedbackResponse response = feedbackService.registrarFeedback(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(
        summary = "Obtener todos los feedbacks recientes",
        description = "Devuelve la lista de los feedbacks más recientes para mostrar en el dashboard del admin"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/recientes")
    public ResponseEntity<List<FeedbackResponse>> verRecientes() {
        return ResponseEntity.ok(feedbackService.obtenerFeedbackReciente());
    }
    
    @Operation(
        summary = "Obtener todos los feedbacks",
        description = "Devuelve la lista de todos los feedbacks para mostrar en el dashboard del admin"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<FeedbackResponse>> verTodos() {
        return ResponseEntity.ok(feedbackService.obtenerTodosLosFeedbacks());
    }
}
