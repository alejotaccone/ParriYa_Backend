package com.parriya.parriya_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parriya.parriya_api.entidades.dto.Feedback.FeedbackRequest;
import com.parriya.parriya_api.entidades.dto.Feedback.FeedbackResponse;
import com.parriya.parriya_api.entidades.Feedback;
import com.parriya.parriya_api.services.*;


@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<Object> crearFeedback(@RequestBody FeedbackRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(feedbackService.registrarFeedback(request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/recientes")
    public ResponseEntity<List<FeedbackResponse>> verRecientes() {
        return ResponseEntity.ok(feedbackService.obtenerFeedbackReciente());
    }

    @GetMapping
    public ResponseEntity<List<FeedbackResponse>> verTodos() {
        return ResponseEntity.ok(feedbackService.obtenerTodosLosFeedbacks());
    }
}
