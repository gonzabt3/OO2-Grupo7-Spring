package com.grupo7.oo2spring.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.grupo7.oo2spring.exception.UsuarioEsEmpleadoException;
import com.grupo7.oo2spring.exception.UsuarioNoEncontradoException;
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
	
	public Empleado prepararEmpleadoDesdeUsuario(int idUsuario) throws UsuarioNoEncontradoException {
	    Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
	    if (usuarioOpt.isEmpty()) {
	        throw new UsuarioNoEncontradoException("Usuario no encontrado");
	    }

	    Usuario usuario = usuarioOpt.get();

	    Empleado empleado = new Empleado();
	    empleado.setIdEmpleado(usuario.getIdUsuario());
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
	        .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));

	    // 2. Verificar si ya tiene el rol de EMPLEADO
	    if (usuario.getRol() == Rol.EMPLEADO) {
	        throw new UsuarioEsEmpleadoException("El usuario ya es un empleado");
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
	    empleado.setRol(Rol.EMPLEADO);
	    empleadoRepository.save(empleado);
	    
	    usuarioRepository.deleteById(idUsuario);

	    // 5. Retornar el empleado recién creado o actualizado
	    return empleado;
	}
	
	@Transactional
	public void sacarPermisosEmpleado(int idEmpleado) throws Exception {
	    Empleado empleado = empleadoRepository.findById(idEmpleado)
	        .orElseThrow(() -> new Exception("Empleado no encontrado"));

	    // Crear usuario nuevo con datos del empleado
	    Usuario nuevoUsuario = new Usuario();
	    nuevoUsuario.setNombre(empleado.getNombre());
	    nuevoUsuario.setNombreUsuario(empleado.getNombreUsuario());
	    nuevoUsuario.setContraseña(empleado.getContraseña());
	    nuevoUsuario.setApellido(empleado.getApellido());
	    nuevoUsuario.setEmail(empleado.getEmail());
	    nuevoUsuario.setDni(empleado.getDni());
	    nuevoUsuario.setRol(Rol.USER);
	    nuevoUsuario.setUsuarioActivo(true);

	    usuarioRepository.save(nuevoUsuario);

	    // Eliminar empleado
	    empleadoRepository.delete(empleado);
	}


	}

