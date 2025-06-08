package com.grupo7.oo2spring.models;

import java.util.Objects;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
public class Cliente extends Usuario {

    private String nroCliente;

    public Cliente(String nombre, String apellido, String dni, String email,
    String nombreUsuario, String contraseña) throws Exception {
        super(nombre, apellido, dni, email, nombreUsuario, contraseña);
        this.setRol(Rol.CLIENTE);
    }

	public String getNroCliente() {
		return nroCliente;
	}

	public void setNroCliente(String nroCliente) {
		this.nroCliente = nroCliente;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nroCliente);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(nroCliente, other.nroCliente);
	}

	@Override
	public String toString() {
		return "Cliente [nroCliente=" + nroCliente + "]";
	}
	
	
	
	
    
    
}