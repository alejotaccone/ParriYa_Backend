
package com.parriya.parriya_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.parriya.parriya_api.services.UsuarioService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parriya.parriya_api.entidades.dto.Producto.ProductoResponse;
import com.parriya.parriya_api.entidades.dto.Usuario.CambiarPasswordRequest;
import com.parriya.parriya_api.entidades.dto.Usuario.UpdatePerfilRequest;
import com.parriya.parriya_api.entidades.dto.Usuario.UsuarioRequest;
import com.parriya.parriya_api.entidades.dto.Usuario.UsuarioResponse;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Endpoint para registrar un nuevo usuario
    @PostMapping
    public ResponseEntity<Object> registrarUsuario(@RequestBody UsuarioRequest request) {
        try {
            UsuarioResponse response = usuarioService.registrarUsuario(request);
            return ResponseEntity.status(201).body(response); // 201 Created
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint para obtener el perfil del usuario
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> getUsuarioPorId(@PathVariable Long id) {
        return usuarioService.getUsuarioPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint exclusivo para actualizar datos de contacto
    @PutMapping("/{id}/perfil")
    public ResponseEntity<Object> updatePerfil(
            @PathVariable Long id, 
            @RequestBody UpdatePerfilRequest request) {
        try {
            UsuarioResponse updated = usuarioService.updatePerfil(id, request);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint para cambiar la contraseña
    @PutMapping("/{id}/password")
    public ResponseEntity<Object> cambiarPassword(
            @PathVariable Long id, 
            @RequestBody CambiarPasswordRequest request) {
        try {
            usuarioService.updatePassword(id, request);
            return ResponseEntity.noContent().build();  // 204 No Content
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint para prender/apagar el favorito
    @PostMapping("/{usuarioId}/favoritos/{productoId}")
    public ResponseEntity<String> gestionarFavorito(@PathVariable Long usuarioId, @PathVariable Long productoId) {
        usuarioService.toggleFavorito(usuarioId, productoId);
        return ResponseEntity.ok("Lista de favoritos actualizada con éxito");
    }

    // Endpoint para mostrar favoritos
    @GetMapping("/{usuarioId}/favoritos")
    public ResponseEntity<List<ProductoResponse>> obtenerFavoritos(@PathVariable Long usuarioId) {
        List<ProductoResponse> favoritos = usuarioService.obtenerFavoritos(usuarioId);
        return ResponseEntity.ok(favoritos);
    }
}
