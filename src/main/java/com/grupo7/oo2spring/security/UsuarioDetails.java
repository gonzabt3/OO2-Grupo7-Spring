package com.grupo7.oo2spring.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.models.UsuarioBase;

public class UsuarioDetails implements UserDetails {

    private final UsuarioBase usuario;

    public UsuarioDetails(UsuarioBase usuarioBase) {
        this.usuario = usuarioBase;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (usuario != null && usuario.getRol() != null) {
            System.out.println("Roles cargados para: " + usuario.getNombreUsuario() + " -> ROLE_" + usuario.getRol().name());
            return List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()));
        }
        System.out.println("No hay roles para usuario: " + (usuario != null ? usuario.getNombreUsuario() : "null"));
        return Collections.emptyList();
    }
    
    public String getEmail() {
        return usuario.getEmail();
    }
    
    public UsuarioBase getUsuario() {
        return usuario;
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
    public boolean isEnabled() { return true; }


@Override
public boolean isAccountNonExpired() { return true; }

@Override
public boolean isAccountNonLocked() { return true; }

@Override
public boolean isCredentialsNonExpired() { return true; }
}


