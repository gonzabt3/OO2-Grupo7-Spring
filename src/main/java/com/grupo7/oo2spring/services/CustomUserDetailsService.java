package com.grupo7.oo2spring.services;

import org.springframework.context.annotation.Bean;

import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.ITicketRepository;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;

import lombok.RequiredArgsConstructor;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.models.UsuarioBase;
import com.grupo7.oo2spring.repositories.IEmpleadoRepository;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;
import com.grupo7.oo2spring.security.UsuarioDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUsuarioRepository usuarioRepository;
    private final IEmpleadoRepository empleadoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombreUsuario(username);
        if (usuario != null) {
            System.out.println("Cargando usuario: " + usuario.getNombreUsuario() + " rol: " + usuario.getRol());
            return new UsuarioDetails(usuario);
        }

        Empleado empleado = empleadoRepository.findEmpleadoAllByNombreUsuario(username);
        if (empleado != null) {
            System.out.println("Cargando empleado: " + empleado.getNombreUsuario() + " rol: " + empleado.getRol());
            return new UsuarioDetails(empleado);
        }

        System.out.println("No se encontró usuario o empleado con username: " + username);
        throw new UsernameNotFoundException("No se encontró usuario: " + username);
    }
    
    
}