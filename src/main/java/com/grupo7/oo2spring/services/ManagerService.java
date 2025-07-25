package com.grupo7.oo2spring.services;

import java.util.Optional;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.grupo7.oo2spring.enums.TipoRol;
import com.grupo7.oo2spring.exception.UsuarioEsEmpleadoException;
import com.grupo7.oo2spring.exception.UsuarioNoEncontradoException;
import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Rol;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.IEmpleadoRepository;
import com.grupo7.oo2spring.repositories.IRolRepository;
import com.grupo7.oo2spring.repositories.ITicketRepository;
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
	private final IRolRepository rolRepository;
	private final UsuarioService usuarioService;
	private final EmpleadoService empleadoService;
	private final ITicketRepository ticketRepository;
	
	 public Empleado crearManager(String nombre, String apellido, String dni, String email, String nombreUsuario, String contraseña, Area area, boolean disponibilidad) throws Exception {
	        Empleado u = new Empleado(nombre, apellido, dni, email, nombreUsuario, contraseña, area, disponibilidad);
	        Rol rolEmpleado = rolRepository.findByTipo(TipoRol.USER);
	        u.setRol(rolEmpleado);
	        return u;
	    }
	
	public Empleado prepararEmpleadoDesdeUsuario(int idUsuario) throws UsuarioNoEncontradoException {
	    Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
	    if (usuarioOpt.isEmpty()) {
	        throw new UsuarioNoEncontradoException("Usuario no encontrado");
	    }

	    Usuario usuario = usuarioOpt.get();

	    Empleado empleado = new Empleado();
	    //empleado.setIdEmpleado(usuario.getId());
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
	    Usuario usuario = usuarioRepository.findById(idUsuario)
	        .orElseThrow(() -> new Exception("Usuario no encontrado"));


	    // Eliminar la fila de la tabla usuario (subclase)
	    ticketRepository.deleteByUsuarioCreador(usuario);
	    entityManager.remove(usuario);
	    entityManager.flush();

	    // Crear un nuevo empleado con el mismo ID (en tabla empleado)
	    Empleado empleado = new Empleado();
	    empleado.setNombre(usuario.getNombre());
	    empleado.setApellido(usuario.getApellido());
	    empleado.setDni(usuario.getDni());
	    empleado.setEmail(usuario.getEmail());
	    empleado.setNombreUsuario(usuario.getNombreUsuario());
	    empleado.setContraseña(usuario.getContraseña());
	    empleado.setArea(datosEmpleado.getArea());
	    empleado.setDisponibilidad(datosEmpleado.isDisponibilidad());

	    // Asignar el rol EMPLEADO
	    empleado.setRol(rolRepository.findByTipo(TipoRol.EMPLEADO));

	    // Guardar empleado en tabla empleado
	    empleado = entityManager.merge(empleado);

	    return empleado;
	}















	
	@Transactional
	public void sacarPermisosEmpleado(int idEmpleado) throws Exception {
	    Empleado empleado = empleadoRepository.findById(idEmpleado)
	        .orElseThrow(() -> new Exception("Empleado no encontrado"));

	    // Crear usuario nuevo con datos del empleado
	    Usuario nuevoUsuario = usuarioService.crearUsuario(empleado.getNombre(), empleado.getApellido(), empleado.getDni(), empleado.getEmail(), empleado.getNombreUsuario(), empleado.getContraseña());
	    nuevoUsuario.setUsuarioActivo(true);
	    
	    // Eliminar empleado
	    empleadoRepository.delete(empleado);
	    empleadoRepository.flush();

	    usuarioRepository.save(nuevoUsuario);


	}


	}

