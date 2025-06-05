package com.grupo7.oo2spring.models;

public enum Rol {
    ADMIN("Admin"),
    EMPLEADO("Empleado"),
    USER("Usuario"),
    CLIENTE("Cliente"),
    MANAGER("Manager");
	
	private final String nombre;
	
	Rol(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return nombre;
	}
}

