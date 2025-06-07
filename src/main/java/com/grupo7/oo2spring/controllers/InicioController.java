package com.grupo7.oo2spring.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.security.UsuarioDetails;

@Controller
public class InicioController {

    @GetMapping({"/", "/inicio"})
    public String raiz(Model model) {
    	  return "index"; 
    }
    

    
}