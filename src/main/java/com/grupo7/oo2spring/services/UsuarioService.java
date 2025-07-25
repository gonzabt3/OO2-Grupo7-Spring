package com.grupo7.oo2spring.services;

import com.grupo7.oo2spring.repositories.IRolRepository;
import com.grupo7.oo2spring.repositories.ITicketRepository;
import com.grupo7.oo2spring.enums.TipoRol;
import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Rol;

import com.grupo7.oo2spring.models.Usuario;

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
    private final IRolRepository rolRepository;
    
    public Usuario crearUsuario(String nombre, String apellido, String dni, String email, String nombreUsuario, String contraseña) throws Exception {
        Usuario u = new Usuario(nombre, apellido, dni, email, nombreUsuario, contraseña);
        Rol rolUser = rolRepository.findByTipo(TipoRol.USER);
        u.setRol(rolUser);
        // Guardar el usuario en repo, etc.
        return u;
    }
    
    public Optional<Usuario> buscarEmpleadoPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                 .filter(u -> u.getRol().getTipo() == TipoRol.EMPLEADO);
    }

    public Usuario getUsuarioByNombreUsuario(String username) {
        return usuarioRepository.findByNombreUsuario(username);
    }
    
    public Optional<Usuario> buscarPorUsernameYPassword(String username, String password) {
    	return usuarioRepository.findByNombreUsuarioAndContraseña(username, password);
    }
    
    public Optional<Usuario> getUsuarioById(int id) {
    	return usuarioRepository.findById(id);
    }
    
    @Transactional
    public void eliminarUsuariosConRolEmpleado() {
        List<Usuario> usuariosConRolEmpleado = usuarioRepository.findByRol(TipoRol.EMPLEADO);
        usuarioRepository.deleteAll(usuariosConRolEmpleado);
    }
   
}
