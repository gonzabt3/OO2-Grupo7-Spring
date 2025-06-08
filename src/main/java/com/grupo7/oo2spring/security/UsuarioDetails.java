package com.grupo7.oo2spring.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.grupo7.oo2spring.models.Usuario;

public class UsuarioDetails implements UserDetails {

    private final Usuario usuario;

    public UsuarioDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	if (usuario != null && usuario.getRol() != null) {
            // Obtiene el nombre del enum (ej. "MANAGER", "CLIENTE")
            String roleName = usuario.getRol().name();
            
            // Construye la autoridad con el prefijo "ROLE_"
            // y en mayúsculas (aunque .name() ya devuelve en mayúsculas por convención)
            return List.of(new SimpleGrantedAuthority("ROLE_" + roleName));
        }
    	return Collections.emptyList();
        //return List.of(new SimpleGrantedAuthority("ROLE_USER")); 
    }

    @Override
    public String getPassword() {
        return usuario.getContraseña();
    }

    @Override
    public String getUsername() {
        return usuario.getNombreUsuario();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}

