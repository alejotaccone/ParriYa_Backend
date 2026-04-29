package com.parriya.parriya_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.parriya.parriya_api.services.UsuarioService;
import com.parriya.parriya_api.entidades.dto.Producto.ProductoResponse;
import com.parriya.parriya_api.entidades.dto.Usuario.CambiarPasswordRequest;
import com.parriya.parriya_api.entidades.dto.Usuario.UpdatePerfilRequest;
import com.parriya.parriya_api.entidades.dto.Usuario.UsuarioResponse;

@RestController
@RequestMapping("/usuario") 
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Método auxiliar para no repetir código en cada endpoint
    private String getEmailAutenticado() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // Endpoint para obtener MI perfil
    @GetMapping("/perfil")
    public ResponseEntity<UsuarioResponse> getMiPerfil() {
        return usuarioService.getMiPerfil(getEmailAutenticado())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint exclusivo para actualizar MIS datos de contacto
    @PutMapping("/perfil")
    public ResponseEntity<Object> updatePerfil(@RequestBody UpdatePerfilRequest request) {
        try {
            UsuarioResponse updated = usuarioService.updatePerfil(getEmailAutenticado(), request);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint para cambiar MI contraseña
    @PutMapping("/password")
    public ResponseEntity<Object> cambiarPassword(@RequestBody CambiarPasswordRequest request) {
        try {
            usuarioService.updatePassword(getEmailAutenticado(), request);
            return ResponseEntity.noContent().build();  // 204 No Content
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint para prender/apagar un favorito MÍO
    @PostMapping("/favoritos/{productoId}")
    public ResponseEntity<String> gestionarFavorito(@PathVariable Long productoId) {
        usuarioService.toggleFavorito(getEmailAutenticado(), productoId);
        return ResponseEntity.ok("Lista de favoritos actualizada con éxito");
    }

    // Endpoint para mostrar MIS favoritos
    @GetMapping("/favoritos")
    public ResponseEntity<List<ProductoResponse>> obtenerMisFavoritos() {
        List<ProductoResponse> favoritos = usuarioService.obtenerFavoritos(getEmailAutenticado());
        return ResponseEntity.ok(favoritos);
    }
}
