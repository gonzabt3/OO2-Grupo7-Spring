package com.grupo7.oo2spring.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.grupo7.oo2spring.models.Rol;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.security.UsuarioDetails;

import jakarta.servlet.http.HttpSession;

@Controller
public class PanelController {
	
	@GetMapping("/panel")
	public String mostrarPanel(Model model) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	    if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
	        UsuarioDetails usuarioDetails = (UsuarioDetails) auth.getPrincipal();
	        Usuario usuario = usuarioDetails.getUsuario();
	        model.addAttribute("usuario", usuario);

	        String rolStr = (usuario.getRol() != null) ? usuario.getRol().toString() : "CLIENTE";
	        model.addAttribute("rol", rolStr);
	    }

	    return "panel";
	}


	}
