package com.grupo7.oo2spring.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final IUsuarioRepository usuarioRepository;
    
    @GetMapping("/misDatos")
    public String verificarUsuario(Model model, @AuthenticationPrincipal UserDetails usuariolog) {
    	String nombre = usuariolog.getUsername();
        Usuario usuariologueado = usuarioRepository.findByNombreUsuario(nombre);
        model.addAttribute("usuariologueado", usuariologueado);
        return "usuario/datos_usuario";
    }
    
    
    
    
    



}
