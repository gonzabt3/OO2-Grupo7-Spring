package com.grupo7.oo2spring.models;

public enum Prioridad {
	SIN_ASIGNAR("Sin Asignar"),
	BAJA("Baja"),
	MEDIA("Media"),
	ALTA("Alta"),
	CRITICA("Critica");
	

	private final String nombre;
	
	Prioridad(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
	
	

}