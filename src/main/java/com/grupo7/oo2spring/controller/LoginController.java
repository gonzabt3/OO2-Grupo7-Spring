package com.grupo7.oo2spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {
	
    @GetMapping("/usuario/login")
    public String mostrarFormularioLogin(HttpServletRequest request, Model model, @RequestParam(required = false) String error) {
    	 if ("true".equals(error)) {
    	        model.addAttribute("error", "Usuario o contraseña incorrectos");
    	    }
    	 
        return "usuario/login"; // formulario login
        
    }
    
    @GetMapping("/error_login")
    public String mostrarErrorLogin(HttpServletRequest request, Model model) {
        String errorMessage = (String) request.getAttribute("error_message");
        model.addAttribute("errorMessage", errorMessage);
        return "usuario/login"; // La vista login donde mostrarás el error
    }

    
}