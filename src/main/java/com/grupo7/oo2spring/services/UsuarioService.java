package com.grupo7.oo2spring.services;

import com.grupo7.oo2spring.repositories.ITicketRepository;
import com.grupo7.oo2spring.models.Cliente;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

 
    @PostConstruct
    public void init() {
        System.out.println("UsuarioService está cargado ✔");
    }
    
    
    public Cliente guardarUsuario(String nombre, String apellido, String dni, String email, String nombreUsuario, String contraseña) {
        Cliente nuevoUsuario = new Cliente();
        nuevoUsuario.setNombreUsuario(nombreUsuario);
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setApellido(apellido);
        nuevoUsuario.setDni(dni);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setContraseña(passwordEncoder.encode(contraseña));
        return usuarioRepository.save(nuevoUsuario);
    }
    
    public Usuario getUsuarioByUsername(String username) {
        return usuarioRepository.findByNombreUsuario(username).orElse(null);
    }
    
    public Optional<Usuario> buscarPorUsernameYPassword(String username, String password) {
    	return usuarioRepository.findByNombreUsuarioAndContraseña(username, password);
    }
   

        public void guardarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
}
