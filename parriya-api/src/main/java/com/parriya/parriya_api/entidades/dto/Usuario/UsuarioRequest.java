package com.parriya.parriya_api.entidades.dto.Usuario;

import lombok.Data;

@Data
public class UsuarioRequest {
    private String nombre;
    private String email;
    private String password;
    private String telefono;
}
