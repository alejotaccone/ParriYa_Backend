package com.parriya.parriya_api.controller;

import com.parriya.parriya_api.entidades.dto.Auth.AuthResponse;
import com.parriya.parriya_api.entidades.dto.Auth.LoginRequest;
import com.parriya.parriya_api.entidades.dto.Auth.RegistroRequest;
import com.parriya.parriya_api.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(
        summary = "Registrar nuevo usuario",
        description = "Crea un nuevo usuario con los datos proporcionados"
    )
    @PostMapping("/registro")
    public ResponseEntity<AuthResponse> registrar(@RequestBody RegistroRequest request) {
        return ResponseEntity.ok(authService.registrar(request));
    }

    @Operation(
        summary = "Iniciar sesión",
        description = "Autentica a un usuario con sus credenciales y devuelve un token JWT"
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
