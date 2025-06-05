package com.grupo7.oo2spring.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.grupo7.oo2spring.models.Cliente;
import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Rol;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpleadoService {
	
	 private final IUsuarioRepository usuarioRepository;
	
	public void sacarPermisosEmpleado(int idEmpleado) throws Exception {
		 Usuario usuario = usuarioRepository.findById(idEmpleado)
			        .orElseThrow(() -> new Exception("Usuario no encontrado"));

			    // Cambiar el rol
			    usuario.setRol(Rol.CLIENTE);


			    // Guardar el usuario actualizado
			    usuarioRepository.save(usuario);
	    
	}

}
