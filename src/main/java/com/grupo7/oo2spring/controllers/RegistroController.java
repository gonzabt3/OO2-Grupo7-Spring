package com.grupo7.oo2spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.grupo7.oo2spring.models.Cliente;
import com.grupo7.oo2spring.models.Rol;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;

@Controller
public class RegistroController {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro() {
        return "/usuario/registro_form";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Cliente usuario) {
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        usuario.setRol(Rol.CLIENTE); // Asignar rol ROLE_CLIENTE

        
        usuarioRepository.save(usuario);
        System.out.println("Usuario registrado: " + usuario.getNombreUsuario());
        return "redirect:/usuario/registro_exito";
    }
    
}

