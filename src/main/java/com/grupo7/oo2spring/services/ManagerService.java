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
import com.grupo7.oo2spring.models.UsuarioBase;
import com.grupo7.oo2spring.repositories.IEmpleadoRepository;
import com.grupo7.oo2spring.repositories.IRolRepository;
import com.grupo7.oo2spring.repositories.ITicketRepository;
import com.grupo7.oo2spring.repositories.IUsuarioBaseRepository;
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
	private final IUsuarioBaseRepository usuarioBaseRepository;
	
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
	    UsuarioBase usuarioBase = usuarioBaseRepository.findById(idUsuario)
	        .orElseThrow(() -> new Exception("Usuario no encontrado"));

	    Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);

	    // Paso A: Eliminar fila de la tabla 'usuario' si existe
	    usuarioOpt.ifPresent(usuarioRepository::delete);

	    // Paso B: Crear nuevo empleado con mismo ID
	    Empleado empleado = new Empleado();
	    empleado.setId(usuarioBase.getId()); // conservar ID para no afectar FK
	    empleado.setNombre(usuarioBase.getNombre());
	    empleado.setApellido(usuarioBase.getApellido());
	    empleado.setDni(usuarioBase.getDni());
	    empleado.setEmail(usuarioBase.getEmail());
	    empleado.setNombreUsuario(usuarioBase.getNombreUsuario());
	    empleado.setContraseña(usuarioBase.getContraseña());
	    empleado.setArea(datosEmpleado.getArea());
	    empleado.setDisponibilidad(datosEmpleado.isDisponibilidad());
	    empleado.setRol(rolRepository.findByTipo(TipoRol.EMPLEADO));

	    // Guardar empleado
	    empleado = empleadoRepository.save(empleado);

	    return empleado;
	}



	@Transactional
	public void sacarPermisosEmpleado(int idEmpleado) throws Exception {
	    Empleado empleado = empleadoRepository.findById(idEmpleado)
	        .orElseThrow(() -> new Exception("Empleado no encontrado"));

	    // Actualizar rol a USUARIO sin borrar filas
	    empleado.setRol(rolRepository.findByTipo(TipoRol.USER));
	    
	    // Guardar cambios sin borrar nada
	    empleadoRepository.save(empleado);
	}




	}

