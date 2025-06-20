package com.grupo7.oo2spring.services;

import com.grupo7.oo2spring.repositories.ITicketRepository;
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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final IUsuarioRepository usuarioRepository;
    
    public Optional<Usuario> buscarEmpleadoPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                 .filter(u -> u.getRol() == Rol.EMPLEADO);
    }

    public Usuario getUsuarioByNombreUsuario(String username) {
        return usuarioRepository.findByNombreUsuario(username);
    }
    
    public Optional<Usuario> buscarPorUsernameYPassword(String username, String password) {
    	return usuarioRepository.findByNombreUsuarioAndContraseña(username, password);
    }
   
}
