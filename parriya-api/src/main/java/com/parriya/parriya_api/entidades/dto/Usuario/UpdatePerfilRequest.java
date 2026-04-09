package com.parriya.parriya_api.entidades.dto.Usuario;

import lombok.Data;

@Data
public class UpdatePerfilRequest {
    private String nombre;
    private String email;
    private String telefono;
}
