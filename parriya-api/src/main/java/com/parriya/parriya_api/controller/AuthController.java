package com.parriya.parriya_api.controller;

import com.parriya.parriya_api.entidades.dto.Auth.AuthResponse;
import com.parriya.parriya_api.entidades.dto.Auth.LoginRequest;
import com.parriya.parriya_api.entidades.dto.Auth.RegistroRequest;
import com.parriya.parriya_api.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(
        summary = "Registrar nuevo usuario",
        description = "Crea un nuevo usuario con los datos proporcionados"
    )
    @PostMapping("/registro")
    public ResponseEntity<AuthResponse> registrar(@Valid @RequestBody RegistroRequest request) {
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

    @Operation(
        summary = "Verificar si un email está registrado",
        description = "Devuelve 200 si el email existe en el sistema, 404 si no está registrado"
    )
    @PostMapping("/verificar-email")
    public ResponseEntity<Void> verificarEmail(@RequestBody LoginRequest request) {
        return authService.existeEmail(request.getEmail())
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @Operation(
        summary = "Cambiar contraseña",
        description = "Actualiza la contraseña del usuario identificado por email"
    )
    @PostMapping("/cambiar-password")
    public ResponseEntity<Void> cambiarPassword(@RequestBody LoginRequest request) {
        authService.cambiarPassword(request.getEmail(), request.getPassword());
        return ResponseEntity.ok().build();
    }
}
