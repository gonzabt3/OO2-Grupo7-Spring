package com.grupo7.oo2spring.controller.rest;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo7.oo2spring.dto.RolDTO;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.models.UsuarioBase;
import com.grupo7.oo2spring.repositories.IRolRepository;
import com.grupo7.oo2spring.security.UsuarioDetails;
import com.grupo7.oo2spring.services.UsuarioService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioRestController {
	


	    private final UsuarioService usuarioService;
	    private final IRolRepository rolRepository;

	    @GetMapping("/{id}")
	    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable int id) {
	        Optional<Usuario> usuario = usuarioService.getUsuarioById(id);
	        if (usuario.isPresent()) {
	            return ResponseEntity.ok(usuario.get());
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    
	    @GetMapping("/getUsuario")
	    public ResponseEntity<Map<String, String>> getUsuario() {
	    	System.out.println("hello");
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        Object principal = auth.getPrincipal();

	        System.out.println("auth: " + principal);

	        if (principal instanceof UsuarioDetails detalles) {
	            UsuarioBase usuario = detalles.getUsuario();
	            String nombreUsuario = usuario.getNombreUsuario();

	            return ResponseEntity.ok(Map.of("nombreUsuario", nombreUsuario));
	        }

	        // Si no está autenticado o no es UsuarioDetails
	        return ResponseEntity.status(401).build();
	    }



	}
