package com.grupo7.oo2spring.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.grupo7.oo2spring.models.Cliente;
import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpleadoService {
	
	 private final IUsuarioRepository usuarioRepository;
	
	public void sacarPermisosEmpleado(int idEmpleado) throws Exception {
	    Empleado empleadoAux = usuarioRepository.findEmpleadoById(idEmpleado)
	        .orElseThrow(() -> new Exception("Empleado no encontrado"));

	    // Crear cliente con datos del empleado
	    Cliente cliente = new Cliente(
	        empleadoAux.getNombre(),
	        empleadoAux.getApellido(),
	        empleadoAux.getDni(),
	        empleadoAux.getEmail(),
	        empleadoAux.getNombreUsuario(),
	        empleadoAux.getContraseña()
	    );
	    
	    // Eliminar empleado
	    usuarioRepository.delete(empleadoAux);
	    
	    // Guardar el cliente
	    usuarioRepository.save(cliente);
	    
	}

}
