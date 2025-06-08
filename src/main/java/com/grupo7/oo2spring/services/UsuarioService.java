package com.grupo7.oo2spring.services;

import com.grupo7.oo2spring.config.CustomUsuario;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService{

    @Autowired
    private IUsuarioRepository usuarioRepository;

    public Usuario getUsuarioByUsername(String username) {
        return usuarioRepository.findByNombreUsuario(username).orElse(null);
    }

        public void guardarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
 
        }
        @Override
		public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
			Usuario user = usuarioRepository.findBynombreUsuario(nombreUsuario);
			if(user== null) {
				throw new UsernameNotFoundException("Usuario no encontrado");
			}else
			return new CustomUsuario(user);
		}
}