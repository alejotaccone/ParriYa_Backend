package com.parriya.parriya_api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parriya.parriya_api.entidades.Producto;
import com.parriya.parriya_api.entidades.Usuario;

import com.parriya.parriya_api.entidades.dto.Usuario.UpdatePerfilRequest;
import com.parriya.parriya_api.entidades.dto.Usuario.UsuarioResponse;
import com.parriya.parriya_api.entidades.dto.Producto.ProductoResponse;
import com.parriya.parriya_api.entidades.dto.Usuario.CambiarPasswordRequest;
import com.parriya.parriya_api.repository.ProductoRepository;
import com.parriya.parriya_api.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Obtener "Mi Perfil" buscando por email
    public Optional<UsuarioResponse> getMiPerfil(String email) {
        return usuarioRepository.findByEmail(email).map(this::mapearAResponse);
    }

    // Actualizar datos de contacto del usuario logueado
    public UsuarioResponse updatePerfil(String email, UpdatePerfilRequest request) throws Exception {
        Usuario usuario = usuarioRepository.findByEmail(email)
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

    // Cambiar la contraseña validando la encriptación
    public void updatePassword(String email, CambiarPasswordRequest request) throws Exception {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPasswordActual(), usuario.getPassword_hash())) {
            throw new Exception("La contraseña actual es incorrecta.");
        }

        if (passwordEncoder.matches(request.getPasswordNuevo(), usuario.getPassword_hash())) {
            throw new Exception("La nueva contraseña no puede ser igual a la contraseña actual.");
        }

        usuario.setPassword_hash(passwordEncoder.encode(request.getPasswordNuevo()));
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void toggleFavorito(String email, Long productoId) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (usuario.getProductosFavoritos().contains(producto)) {
            usuario.getProductosFavoritos().remove(producto);
        } else {
            usuario.getProductosFavoritos().add(producto);
        }

        usuarioRepository.save(usuario);
    }

    public List<ProductoResponse> obtenerFavoritos(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                
        List<ProductoResponse> favoritosDTO = new ArrayList<>();
        
        for (Producto p : usuario.getProductosFavoritos()) {
            ProductoResponse pRes = new ProductoResponse();
            pRes.setId(p.getId());
            pRes.setNombre(p.getNombre());
            pRes.setDescripcion(p.getDescripcion());
            pRes.setPrecio(p.getPrecio());
            pRes.setStock(p.getStock());
            pRes.setEstado(p.isEstado());
            pRes.setImgUrl(p.getImgUrl());
            pRes.setCategoriaId(p.getCategoria().getId());
            
            favoritosDTO.add(pRes);
        }
        
        return favoritosDTO;
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