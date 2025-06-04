package com.grupo7.oo2spring.services;

import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    public Usuario getUsuarioByUsername(String username) {
        return usuarioRepository.findByNombreUsuario(username).orElse(null);
    }

        public void guardarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
}