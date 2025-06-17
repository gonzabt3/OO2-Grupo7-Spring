package com.grupo7.oo2spring.services;

//<<<<<<< HEAD
//=======
//import com.grupo7.oo2spring.repositories.ITicketRepository;
//>>>>>>> refs/remotes/origin/pauche
import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Rol;
import com.grupo7.oo2spring.models.Ticket;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.ITicketRepository;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final IUsuarioRepository usuarioRepository;
//<<<<<<< HEAD
//    @Transactional
//    public Empleado convertirAEmpleado(int idUsuario, Empleado datosEmpleado) throws Exception {
//
//    	Usuario usuario = usuarioRepository.findById(idUsuario)
//    	        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
//
//    	    usuario.setRol(Rol.EMPLEADO);
//    	    usuarioRepository.save(usuario);
//
//    	    // Inserto fila empleado con el mismo ID
//    	    entityManager.createNativeQuery("INSERT INTO empleado (id_usuario, area, disponibilidad) VALUES (?, ?, ?)")
//    	        .setParameter(1, usuario.getIdUsuario())
//    	        .setParameter(2, datosEmpleado.getArea().toString())  // Ajusta según tipo de campo
//    	        .setParameter(3, datosEmpleado.isDisponibilidad())
//    	        .executeUpdate();
//
//    	    return entityManager.find(Empleado.class, usuario.getIdUsuario());
//    	}
//=======
//    private final PasswordEncoder passwordEncoder;
//    @PersistenceContext
//    private EntityManager entityManager;
//    
//    
//    public Usuario guardarUsuario(String nombre, String apellido, String dni, String email, String nombreUsuario, String contraseña) {
//        Usuario nuevoUsuario = new Usuario();
//        nuevoUsuario.setNombreUsuario(nombreUsuario);
//        nuevoUsuario.setNombre(nombre);
//        nuevoUsuario.setApellido(apellido);
//        nuevoUsuario.setDni(dni);
//        nuevoUsuario.setEmail(email);
//        nuevoUsuario.setContraseña(passwordEncoder.encode(contraseña));
//        return usuarioRepository.save(nuevoUsuario);
//    }
//    
//    @Transactional
//    public Empleado convertirAEmpleado(int idUsuario, Empleado datosEmpleado) throws Exception {
//
//    	Usuario usuario = usuarioRepository.findById(idUsuario)
//    	        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
//
//    	    usuario.setRol(Rol.EMPLEADO);
//    	    usuarioRepository.save(usuario);
//
//    	    // Inserto fila empleado con el mismo ID
//    	    entityManager.createNativeQuery("INSERT INTO empleado (id_usuario, area, disponibilidad) VALUES (?, ?, ?)")
//    	        .setParameter(1, usuario.getIdUsuario())
//    	        .setParameter(2, datosEmpleado.getArea().toString())  // Ajusta según tipo de campo
//    	        .setParameter(3, datosEmpleado.isDisponibilidad())
//    	        .executeUpdate();
//
//    	    return entityManager.find(Empleado.class, usuario.getIdUsuario());
//    	}

    
    public Optional<Usuario> buscarEmpleadoPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                 .filter(u -> u.getRol() == Rol.EMPLEADO);
    }

//<<<<<<< HEAD
    public Usuario getUsuarioByNombreUsuario(String username) {
        return usuarioRepository.findByNombreUsuario(username);
    }

//
//    public Usuario getUsuarioByNombreUsuario(String username) {
//        return usuarioRepository.findByNombreUsuario(username);
//>>>>>>> refs/remotes/origin/pauche
//    }
    
    public Optional<Usuario> buscarPorUsernameYPassword(String username, String password) {
    	return usuarioRepository.findByNombreUsuarioAndContraseña(username, password);
    }
}
