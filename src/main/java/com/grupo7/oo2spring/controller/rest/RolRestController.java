package com.grupo7.oo2spring.controller.rest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo7.oo2spring.dto.RolDTO;
import com.grupo7.oo2spring.models.UsuarioBase;
import com.grupo7.oo2spring.repositories.IRolRepository;
import com.grupo7.oo2spring.security.UsuarioDetails;
import com.grupo7.oo2spring.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RolRestController {
	
	 @GetMapping("/api/rol")
	    public RolDTO getRol() {
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        Object principal = auth.getPrincipal();
	        

	        if (principal instanceof UsuarioDetails detalles) {
	            UsuarioBase usuario = detalles.getUsuario(); // puede ser Usuario o Empleado
	            String rolNombre = usuario.getRol().getTipo().getNombre(); 
	            return new RolDTO(rolNombre.toUpperCase());
	        }

	        // Si no hay usuario logueado o no es UsuarioBase, devolvemos un rol por defecto o vacío
	        return new RolDTO("USER");
	    }

}
