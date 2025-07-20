package com.grupo7.oo2spring.models;

import java.util.Objects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Usuario extends UsuarioBase {

    @Column(nullable = false)
    private boolean usuarioActivo; //va en Usuario xq no aplica a Empleado, solo a un Usuario normal

    
    public Usuario(String nombre, String apellido, String dni, String email, String nombreUsuario, String contraseña, Rol rol) throws Exception {
    	super(nombre, apellido, dni, email, nombreUsuario, contraseña, rol);
    	this.usuarioActivo = false;
    }
  



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return super.getId() == other.getId() && usuarioActivo == other.usuarioActivo;
	}
   
   


   
}