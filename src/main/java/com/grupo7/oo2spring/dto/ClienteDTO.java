package com.grupo7.oo2spring.dto;

import java.util.Objects;

import com.grupo7.oo2spring.models.Usuario;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ClienteDTO extends UsuarioDTO {

    private String nroCliente;

    public ClienteDTO(String nombre, String apellido, String dni, String email,
    String nombreUsuario, String contraseña, String nroCliente) throws Exception {
        super(nombre, apellido, dni, email, nombreUsuario, contraseña);
        this.nroCliente = nroCliente;
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
		ClienteDTO other = (ClienteDTO) obj;
		return Objects.equals(nroCliente, other.nroCliente);
	}

	@Override
	public String toString() {
		return "Cliente [nroCliente=" + nroCliente + "]";
	}

    
    
}