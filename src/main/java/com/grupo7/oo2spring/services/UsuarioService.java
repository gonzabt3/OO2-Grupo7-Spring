package com.grupo7.oo2spring.services;

import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.ITicketRepository;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;


@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final IUsuarioRepository usuarioRepository;

 
    public Usuario guardarUsuario(String nombre, String apellido, String dni, String email, String nombreUsuario, String contraseña) {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setApellido(apellido);
        nuevoUsuario.setDni(dni);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setNombreUsuario(nombreUsuario);
        nuevoUsuario.setContraseña(contraseña);

        return usuarioRepository.save(nuevoUsuario);
    }
    
    public Usuario getUsuarioByUsername(String username) {
        return usuarioRepository.findByNombreUsuario(username).orElse(null);
    }

}
