package com.grupo7.oo2spring.controller;



import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.grupo7.oo2spring.services.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {
	
	private final UsuarioService usuarioService;


    @GetMapping("/usuario/login")
    public String mostrarFormularioLogin(HttpServletRequest request, Model model, @RequestParam(required = false) String error) {
    	 if ("true".equals(error)) {
    	        model.addAttribute("error", "Usuario o contraseña incorrectos");
    	    }
        return "/usuario/login"; // formulario login
    }
    
}