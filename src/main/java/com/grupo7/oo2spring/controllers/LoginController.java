package com.grupo7.oo2spring.controllers;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



import com.grupo7.oo2spring.services.UsuarioService;


import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {
	
	private final UsuarioService usuarioService;


    @GetMapping("/usuario/login")
    public String mostrarFormularioLogin(Model model, String error) {
    	 if (error != null) {
    	        model.addAttribute("error", "Usuario o contraseña incorrectos");
    	    }
        return "/usuario/login"; // formulario login
    }
    
}