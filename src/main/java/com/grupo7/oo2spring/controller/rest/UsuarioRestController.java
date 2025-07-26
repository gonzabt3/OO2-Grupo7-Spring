package com.grupo7.oo2spring.controller.rest;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo7.oo2spring.dto.RolDTO;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.models.UsuarioBase;
import com.grupo7.oo2spring.repositories.IRolRepository;
import com.grupo7.oo2spring.repositories.IUsuarioBaseRepository;
import com.grupo7.oo2spring.security.UsuarioDetails;
import com.grupo7.oo2spring.services.UsuarioService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioRestController {
	


	    private final UsuarioService usuarioService;
	    private final IRolRepository rolRepository;
	    private final IUsuarioBaseRepository usuarioBaseRepository;

	    @GetMapping("/{id}")
	    public ResponseEntity<UsuarioBase> obtenerUsuario(@PathVariable int id) {
	        Optional<UsuarioBase> usuario = usuarioBaseRepository.findById(id);
	        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
	    
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> eliminarUsuario(@PathVariable int id) {
	        Optional<Usuario> usuario = usuarioService.getUsuarioById(id);
	        if (usuario.isPresent()) {
	            usuarioService.deleteUsuario(id);
	            return ResponseEntity.noContent().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    
	    @PutMapping("/{id}")
	    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable int id, @RequestBody Usuario usuarioActualizado) {
	        Optional<Usuario> usuarioOptional = usuarioService.getUsuarioById(id);
	        if (usuarioOptional.isPresent()) {
	            Usuario existente = usuarioOptional.get();

	            // Solo como ejemplo: actualizar nombre y email
	            existente.setNombre(usuarioActualizado.getNombre());
	            existente.setNombreUsuario(usuarioActualizado.getNombreUsuario());
	            existente.setContraseña(usuarioActualizado.getContraseña());
	            // Agrega más campos si es necesario

	            Usuario guardado = usuarioService.saveOrUpdate(existente);
	            return ResponseEntity.ok(guardado);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }



	}
