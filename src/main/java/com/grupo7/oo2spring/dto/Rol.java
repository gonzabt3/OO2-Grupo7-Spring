package com.grupo7.oo2spring.dto;

public enum Rol {
    EMPLEADO("Empleado"),
    USER("Usuario"),
    MANAGER("Manager");
	
	
	private final String nombre;
	
	Rol(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return nombre;
	}
}

