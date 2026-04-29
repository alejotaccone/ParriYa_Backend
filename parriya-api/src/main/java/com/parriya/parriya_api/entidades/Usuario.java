package com.parriya.parriya_api.entidades;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Usuario implements UserDetails { // <-- 1. Implementamos UserDetails

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nombre;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password_hash;

	private String telefono;

	private Date fecha_registro;

	private String rol;

	@ManyToMany
    @JoinTable(
        name = "usuario_favoritos",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<Producto> productosFavoritos = new ArrayList<>();


    //  MÉTODOS DE USERDETAILS (SPRING SECURITY)

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 2. Spring necesita el prefijo "ROLE_" (ej: ROLE_CLIENTE o ROLE_ADMIN)
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.rol));
    }

    @Override
    public String getUsername() {
        // 3. Le decimos a Spring que el nombre de usuario para el login es el email
        return this.email;
    }

    @Override
    public String getPassword() {
        // 4. Le apuntamos a tu campo "password_hash"
        return this.password_hash;
    }

    // Controles de cuenta activa. Los dejamos en 'true' para que no bloquee los accesos.
    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
