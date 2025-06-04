package com.grupo7.oo2spring.dto;

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