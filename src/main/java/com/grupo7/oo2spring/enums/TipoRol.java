package com.grupo7.oo2spring.enums;

public enum TipoRol {
	EMPLEADO("Empleado"),
    USER("Usuario"),
    MANAGER("Manager");
	
	private final String nombre;
	
	TipoRol(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
}
