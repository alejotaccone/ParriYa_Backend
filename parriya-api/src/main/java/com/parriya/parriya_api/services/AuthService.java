package com.parriya.parriya_api.services;

import com.parriya.parriya_api.security.JwtService;
import com.parriya.parriya_api.entidades.Usuario;
import com.parriya.parriya_api.entidades.dto.Auth.AuthResponse;
import com.parriya.parriya_api.entidades.dto.Auth.LoginRequest;
import com.parriya.parriya_api.entidades.dto.Auth.RegistroRequest;
import com.parriya.parriya_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse registrar(RegistroRequest request) {
        // Validamos que la contraseña no sea igual al email ni al nombre
        if (request.getPassword().trim().equalsIgnoreCase(request.getEmail().trim()) ||
            request.getPassword().trim().equalsIgnoreCase(request.getNombre().trim())) {
            throw new RuntimeException("La contraseña no puede ser igual al correo electrónico o al nombre");
        }

        // Validamos si el email ya existe
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Validamos si el nombre de usuario ya existe
        if (usuarioRepository.findByNombre(request.getNombre()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setTelefono(request.getTelefono());
        // Encriptamos la contraseña antes de guardarla
        usuario.setPassword_hash(passwordEncoder.encode(request.getPassword()));
        usuario.setRol("CLIENTE"); // Rol por defecto
        usuario.setFecha_registro(new Date());

        usuarioRepository.save(usuario);

        // Generamos el token para que ya quede logueado al registrarse
        String token = jwtService.generateToken(usuario);
        
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        return response;
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Usuario usuario = usuarioRepository.findByEmailOrNombre(request.getEmail(), request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtService.generateToken(usuario);

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        return response;
    }
}
