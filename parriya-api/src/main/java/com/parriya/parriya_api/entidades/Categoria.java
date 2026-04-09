package com.parriya.parriya_api.entidades;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String imgUrl;

    public Categoria() {
    }

    public Categoria(String nombre, String imgUrl) {
        this.nombre = nombre;
        this.imgUrl = imgUrl;
    }
}
