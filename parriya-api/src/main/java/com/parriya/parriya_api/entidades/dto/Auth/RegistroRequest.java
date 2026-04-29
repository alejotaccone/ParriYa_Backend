package com.parriya.parriya_api.entidades.dto.Auth;

import lombok.Data;

@Data
public class RegistroRequest {
    private String nombre;
    private String email;
    private String password;
    private String telefono;
}
