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

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // Método auxiliar para no repetir código en cada endpoint
    private String getEmailAutenticado() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // Endpoint para obtener MI perfil
    @Operation(
        summary = "Obtener perfil del usuario",
        description = "Devuelve los datos de contacto y rol del usuario logueado usando su token JWT"
    )
    @GetMapping("/perfil")
    public ResponseEntity<UsuarioResponse> getMiPerfil() {
        return usuarioService.getMiPerfil(getEmailAutenticado())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint exclusivo para actualizar MIS datos de contacto
    @Operation(
        summary = "Actualizar perfil del usuario",
        description = "Actualiza los datos de contacto del usuario logueado usando su token JWT"
    )
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
    @Operation(
        summary = "Cambiar contraseña del usuario",
        description = "Cambia la contraseña del usuario logueado usando su token JWT"
    )
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
    @Operation(
        summary = "Gestionar favorito",
        description = "Agrega o quita un producto de la lista de favoritos del usuario logueado usando su token JWT"
    )
    @PostMapping("/favoritos/{productoId}")
    public ResponseEntity<String> gestionarFavorito(@PathVariable Long productoId) {
        usuarioService.toggleFavorito(getEmailAutenticado(), productoId);
        return ResponseEntity.ok("Lista de favoritos actualizada con éxito");
    }

    // Endpoint para mostrar MIS favoritos
    @Operation(
        summary = "Obtener favoritos",
        description = "Devuelve la lista de productos favoritos del usuario logueado usando su token JWT"
    )
    @GetMapping("/favoritos")
    public ResponseEntity<List<ProductoResponse>> obtenerMisFavoritos() {
        List<ProductoResponse> favoritos = usuarioService.obtenerFavoritos(getEmailAutenticado());
        return ResponseEntity.ok(favoritos);
    }
}
