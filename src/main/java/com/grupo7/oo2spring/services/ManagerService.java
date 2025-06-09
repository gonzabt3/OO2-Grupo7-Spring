package com.grupo7.oo2spring.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Rol;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.IEmpleadoRepository;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManagerService {
	
	private final IUsuarioRepository usuarioRepository;
	private final IEmpleadoRepository empleadoRepository;
	private final EntityManager entityManager;
	
	public Empleado prepararEmpleadoDesdeUsuario(int idUsuario) throws Exception {
	    Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
	    if (usuarioOpt.isEmpty()) {
	        throw new Exception("Usuario no encontrado");
	    }

	    Usuario usuario = usuarioOpt.get();

	    Empleado empleado = new Empleado();
	    empleado.setIdUsuario(usuario.getIdUsuario());
	    empleado.setNombre(usuario.getNombre());
	    empleado.setApellido(usuario.getApellido());
	    empleado.setDni(usuario.getDni());
	    empleado.setEmail(usuario.getEmail());
	    empleado.setNombreUsuario(usuario.getNombreUsuario());
	    empleado.setContraseña(usuario.getContraseña());

	    // NO guarda nada aún
	    return empleado;
	}
	
	@Transactional
	public Empleado convertirUsuarioAEmpleado(int idUsuario, Empleado datosEmpleado) throws Exception {
		// 1. Buscar el usuario por ID
	    Usuario usuario = usuarioRepository.findById(idUsuario)
	        .orElseThrow(() -> new Exception("Usuario no encontrado"));

	    // 2. Verificar si ya tiene el rol de EMPLEADO
	    if (usuario.getRol() == Rol.EMPLEADO) {
	        throw new Exception("El usuario ya es un empleado");
	    }

	    Empleado empleado;

	    // 3. Verificar si ya existe una fila en la tabla empleado
	    if (empleadoRepository.existsById(idUsuario)) {
	        // Si existe, actualizarla
	        empleado = empleadoRepository.findById(idUsuario)
	            .orElseThrow(() -> new Exception("Error al recuperar datos del empleado existente"));

	        empleado.setArea(datosEmpleado.getArea());
	        empleado.setDisponibilidad(datosEmpleado.isDisponibilidad());

	        empleado = empleadoRepository.save(empleado); // Actualiza
	    } else {
	        // Si no existe, crear una nueva instancia
	        empleado = new Empleado();
	        empleado.setIdUsuario(idUsuario); // hereda de Usuario
	        empleado.setArea(datosEmpleado.getArea());
	        empleado.setDisponibilidad(datosEmpleado.isDisponibilidad());
	        empleado.setNombre(usuario.getNombre());
	        empleado.setApellido(usuario.getApellido());
	        empleado.setDni(usuario.getDni());
	        empleado.setEmail(usuario.getEmail());
	        empleado.setNombreUsuario(usuario.getNombreUsuario());
	        empleado.setContraseña(usuario.getContraseña());
	        empleado.setRol(Rol.EMPLEADO);
	    }

	    // 4. Cambiar rol en el usuario base (por si no lo setea bien al persist)
	    usuario.setRol(Rol.EMPLEADO);
	    usuarioRepository.save(usuario);

	    // 5. Retornar el empleado recién creado o actualizado
	    return empleado;
	}
	
	@Transactional
	public void sacarPermisosEmpleado(int idEmpleado) throws Exception {
		Empleado empleado = empleadoRepository.findById(idEmpleado)
	            .orElseThrow(() -> new Exception("Empleado no encontrado"));

	    Usuario usuario = usuarioRepository.findById(empleado.getIdUsuario())
	            .orElseThrow(() -> new Exception("Usuario no encontrado"));

	    // Cambiar el rol del usuario a CLIENTE
	    usuario.setRol(Rol.USER);
	    usuarioRepository.save(usuario);

	    // Borrar el registro Empleado (tabla hija)
	    //empleadoRepository.delete(empleado);

	}


	}

