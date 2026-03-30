package com.parriya.parriya_api.entidades;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoria_id;

    private String nombre;
    private String imgUrl;

    @OneToMany(mappedBy = "categoria")
    private List<Producto> productos;

    public Categoria(String nombre, String imgUrl){
        this.nombre = nombre;
        this.imgUrl = imgUrl;  
    }
}
