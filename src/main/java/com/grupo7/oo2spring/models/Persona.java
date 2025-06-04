package com.grupo7.oo2spring.models;

import java.util.Objects;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor(force = true)
public class Persona {

    private String nombre;

    private String apellido;

    private String dni;

    private String email;
	
	public Persona(String nombre, String apellido, String dni, String email) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.email = email;
	}
	

	


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	@Override
	public int hashCode() {
		return Objects.hash(apellido, dni, email, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		return Objects.equals(apellido, other.apellido) && Objects.equals(dni, other.dni)
				&& Objects.equals(email, other.email) && Objects.equals(nombre, other.nombre);
	}
    
    
}