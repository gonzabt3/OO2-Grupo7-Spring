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
	
	public Empleado prepararEmpleadoDesdeUsuario(int idUsuario) throws Exception {
	    Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
	    if (usuarioOpt.isEmpty()) {
	        throw new UsuarioNoEncontradoException("El Usuario no se encuentra en el sistema");
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
	        .orElseThrow(() -> new UsuarioNoEncontradoException("El Usuario no se encuentra en el sistema"));

	    // Verificar si ya tiene el rol de EMPLEADO
	    if (usuario.getRol() == Rol.EMPLEADO) {
	        throw new UsuarioEsEmpleadoException("El usuario con ID " + idUsuario + " ya es un Empleado.");
	    }
	    usuario.setRol(Rol.EMPLEADO);
	    usuarioRepository.save(usuario);
	    Empleado empleado = entityManager.find(Empleado.class, idUsuario);;

	    // Verificar si ya existe una fila en la tabla empleado
	    if (empleado!=null) {
	        // Si existe, actualizarla
	        empleado = empleadoRepository.findById(idUsuario)
	            .orElseThrow(() -> new Exception("Error al recuperar datos del empleado existente"));

	        empleado.setArea(datosEmpleado.getArea());
	        empleado.setDisponibilidad(datosEmpleado.isDisponibilidad());
	    } else {
	    	entityManager.createNativeQuery("INSERT INTO empleado (id_usuario, area, disponibilidad) VALUES (?, ?, ?)")
	        .setParameter(1, usuario.getIdUsuario())
	        .setParameter(2, datosEmpleado.getArea().name())
	        .setParameter(3, datosEmpleado.isDisponibilidad())
	        .executeUpdate();
	    }

	    // Guardar el empleado (crea o actualiza)
	    //empleado = empleadoRepository.save(empleado);

	    // Cambiar rol a EMPLEADO
	    

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