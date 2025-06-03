package com.grupo7.oo2spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository userRepository;

    
    public Usuario guardarUsuario(String nombre, String apellido, String dni, String email, String nombreUsuario, String contraseña) {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setApellido(apellido);
        nuevoUsuario.setDni(dni);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setNombreUsuario(nombreUsuario);
        nuevoUsuario.setContraseña(contraseña); // ¡Importante! En una aplicación real, deberías hashear la contraseña

        return userRepository.save(nuevoUsuario);
    }

}
