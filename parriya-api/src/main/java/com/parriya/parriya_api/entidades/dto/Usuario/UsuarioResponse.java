package com.parriya.parriya_api.entidades.dto.Usuario;

import java.util.Date;

import lombok.Data;

@Data
public class UsuarioResponse {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private Date fecha_registro;
    private String rol;
}
