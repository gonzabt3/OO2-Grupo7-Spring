package com.grupo7.oo2spring.dto;

public enum Rol {
    ROLE_ADMIN("Admin"),
    ROLE_EMPLEADO("Empleado"),
    ROLE_USER("Usuario");
	
	private final String nombre;
	
	Rol(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return nombre;
	}
}

