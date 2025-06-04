package com.grupo7.oo2spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String mostrarFormularioRegistro() {
        return "registro"; // carga registro.html
    }

    @PostMapping
    public String registrarUsuario(@ModelAttribute Usuario usuario) {
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        usuarioRepository.save(usuario);
        return "redirect:/login"; // o a donde quieras redirigir
    }
}

