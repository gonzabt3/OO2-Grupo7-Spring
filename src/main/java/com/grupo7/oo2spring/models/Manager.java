package com.grupo7.oo2spring.models;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Manager extends Usuario {
	
    public Manager(String nombre, String apellido, String dni, String email,String nombreUsuario, String password) throws Exception {
      super(nombre, apellido, dni, email, nombreUsuario, password);
      this.setRol(Rol.MANAGER);
    }
    
}