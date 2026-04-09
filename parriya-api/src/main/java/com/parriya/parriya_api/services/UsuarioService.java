package com.parriya.parriya_api.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parriya.parriya_api.entidades.Usuario;

import com.parriya.parriya_api.entidades.dto.Usuario.UpdatePerfilRequest;
import com.parriya.parriya_api.entidades.dto.Usuario.UsuarioRequest;
import com.parriya.parriya_api.entidades.dto.Usuario.UsuarioResponse;
import com.parriya.parriya_api.entidades.dto.Usuario.CambiarPasswordRequest;
import com.parriya.parriya_api.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Registrar un nuevo usuario
    public UsuarioResponse registrarUsuario(UsuarioRequest request) throws Exception {
        
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(request.getEmail());
        if (usuarioExistente.isPresent()) {
            throw new Exception("El correo ya está registrado en ParriYa!");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(request.getNombre());
        nuevoUsuario.setEmail(request.getEmail());
        nuevoUsuario.setTelefono(request.getTelefono());
        nuevoUsuario.setPassword_hash(request.getPassword()); //Falta encriptar
        nuevoUsuario.setFecha_registro(new Date());
        nuevoUsuario.setRol("CLIENTE");

        Usuario guardado = usuarioRepository.save(nuevoUsuario);
        return mapearAResponse(guardado);
    }

    // Obtener usuario por ID 
    public Optional<UsuarioResponse> getUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).map(this::mapearAResponse);
    }

    // Actualizar datos de contacto
    public UsuarioResponse updatePerfil(Long id, UpdatePerfilRequest request) throws Exception {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        if (!usuario.getEmail().equals(request.getEmail())) {
            Optional<Usuario> emailOcupado = usuarioRepository.findByEmail(request.getEmail());
            if (emailOcupado.isPresent()) {
                throw new Exception("El nuevo correo ya está en uso por otra cuenta.");
            }
        }

        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setTelefono(request.getTelefono());

        Usuario guardado = usuarioRepository.save(usuario);
        return mapearAResponse(guardado);
    }

    // Cambiar la contraseña
    public void updatePassword(Long id, CambiarPasswordRequest request) throws Exception {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        if (!usuario.getPassword_hash().equals(request.getPasswordActual())) {
            throw new Exception("La contraseña actual es incorrecta.");
        }

        usuario.setPassword_hash(request.getPasswordNuevo());
        usuarioRepository.save(usuario);
    }

    // Método de mapeo interno (Filtrando el password)
    private UsuarioResponse mapearAResponse(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setNombre(usuario.getNombre());
        response.setEmail(usuario.getEmail());
        response.setTelefono(usuario.getTelefono());
        response.setFecha_registro(usuario.getFecha_registro());
        response.setRol(usuario.getRol());
        return response;
    }
}