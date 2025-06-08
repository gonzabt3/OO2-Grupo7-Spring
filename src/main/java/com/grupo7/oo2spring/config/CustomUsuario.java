package com.grupo7.oo2spring.config;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.grupo7.oo2spring.models.Usuario;
public class CustomUsuario implements UserDetails {
	private Usuario user;
	
	

	public CustomUsuario(Usuario user) {
		super();
		this.user = user;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRol().getDescripcion());
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getContraseña();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getNombreUsuario();
	}

}
