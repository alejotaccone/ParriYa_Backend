package com.parriya.parriya_api.entidades.dto.Usuario;

import lombok.Data;

@Data
public class CambiarPasswordRequest {
    private String passwordActual;
    private String passwordNuevo;
}
