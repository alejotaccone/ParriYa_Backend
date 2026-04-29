package com.parriya.parriya_api.entidades.dto.Auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}