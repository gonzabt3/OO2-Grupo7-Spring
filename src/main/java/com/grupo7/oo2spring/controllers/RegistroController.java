package com.grupo7.oo2spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public String registrarUsuario(@ModelAttribute Usuario usuario) {
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        usuarioRepository.save(usuario);
        System.out.println("Usuario registrado: " + usuario.getUsername());
        return "redirect:/usuario/registro_exito";
    }
    
    @GetMapping("/usuario/registro_exito")
    public String mostrarRegistroExito() {
    	 System.out.println(">> MOSTRANDO /usuario/registro_exito");
        return "/usuario/registro_exito"; 
    }
}

