package com.grupo7.oo2spring.controllers.rest;

import com.grupo7.oo2spring.dto.LoginDTO;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginRestController {

	private final UsuarioService usuarioService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDTO request, HttpSession session) {
	    Optional<Usuario> usuarioOpt = usuarioService.buscarPorUsernameYPassword(
	            request.username(), request.password());

	    if (usuarioOpt.isPresent()) {
	        Usuario usuario = usuarioOpt.get();
	        session.setAttribute("usuario", usuario);
	        session.setAttribute("rolUsuario", usuario.getRol());

	        return ResponseEntity.ok("Login exitoso para: " + usuario.getNombreUsuario());
	    } else {
	        return ResponseEntity.status(401).body("Usuario o contraseña incorrectos");
	    }
	}
    
}
