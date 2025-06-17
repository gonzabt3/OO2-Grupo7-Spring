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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;

    @Column(nullable = false)
    private boolean usuarioActivo; //va en Usuario xq no aplica a Empleado, solo a un Usuario normal

    
    public Usuario(String nombre, String apellido, String dni, String email, String nombreUsuario, String contraseña) throws Exception {
    	super(nombre, apellido, dni, email, nombreUsuario, contraseña);
    	this.setRol(Rol.USER);
    	this.usuarioActivo = false;
    }
   

    
   public int getIdUsuario() {
     return idUsuario;
   }

   public void setIdUsuario(int idUsuario) {
     this.idUsuario = idUsuario;
   }

   
   public void setUsuarioActivo(boolean bool) {
	   this.usuarioActivo = bool;
   }
   
   public boolean getUsuarioActivo() {
	   return this.usuarioActivo;
   }



@Override
public int hashCode() {
	return Objects.hash(idUsuario, usuarioActivo);
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
	return idUsuario == other.idUsuario && usuarioActivo == other.usuarioActivo;
}
   
   


   
}