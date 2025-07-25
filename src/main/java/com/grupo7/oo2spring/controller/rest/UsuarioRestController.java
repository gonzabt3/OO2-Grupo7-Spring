package com.grupo7.oo2spring.controller.rest;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioRestController {
	


	    private final UsuarioService usuarioService;

	    @GetMapping("/{id}")
	    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable int id) {
	        Optional<Usuario> usuario = usuarioService.getUsuarioById(id);
	        if (usuario.isPresent()) {
	            return ResponseEntity.ok(usuario.get());
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	}
