package com.grupo7.oo2spring.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.grupo7.oo2spring.models.Cliente;
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
		 // Buscar el usuario por ID
	    Usuario usuario = usuarioRepository.findById(idUsuario)
	        .orElseThrow(() -> new Exception("Usuario no encontrado"));

	    // Verificar si ya tiene el rol de EMPLEADO
	    if (usuario.getRol() == Rol.EMPLEADO) {
	        throw new Exception("El usuario ya es un empleado");
	    }

	    Empleado empleado;

	    // Verificar si ya existe una fila en la tabla empleado
	    if (empleadoRepository.existsById(idUsuario)) {
	        // Si existe, actualizarla
	        empleado = empleadoRepository.findById(idUsuario)
	            .orElseThrow(() -> new Exception("Error al recuperar datos del empleado existente"));

	        empleado.setArea(datosEmpleado.getArea());
	        empleado.setDisponibilidad(datosEmpleado.isDisponibilidad());
	    } else {
	        // Si no existe, crear una nueva
	        empleado = new Empleado();
	        empleado.setIdUsuario(idUsuario); // o setId(idUsuario) si no tenés relación directa
	        empleado.setArea(datosEmpleado.getArea());
	        empleado.setDisponibilidad(datosEmpleado.isDisponibilidad());
	    }

	    // Guardar el empleado (crea o actualiza)
	    empleado = empleadoRepository.save(empleado);

	    // Cambiar rol a EMPLEADO
	    usuario.setRol(Rol.EMPLEADO);
	    usuarioRepository.save(usuario);

	    // Devolver el empleado actualizado/creado
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

