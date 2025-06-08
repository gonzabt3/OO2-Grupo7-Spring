package com.grupo7.oo2spring.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.ITicketRepository;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;
import com.grupo7.oo2spring.services.UsuarioService;

import jakarta.servlet.http.HttpSession;
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