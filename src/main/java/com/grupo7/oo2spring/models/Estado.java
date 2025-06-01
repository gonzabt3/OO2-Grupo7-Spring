package com.grupo7.oo2spring.models;

public enum Estado {
	ABIERTO("Abierto"),
	PENDIENTE("Pendiente"),
	CERRADO("Cerrado"),
	RESUELTO("Resuelto");
	
	private final String nombre;
	
	Estado(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return nombre;
	}

}