package com.grupo7.oo2spring.models;

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

