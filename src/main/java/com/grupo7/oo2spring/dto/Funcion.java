package com.grupo7.oo2spring.dto;

public enum Funcion {
	SEGUIMIENTO("Seguimiento"),
	VALIDACION("Validacion"),
	AUDITORIA("Auditoria"),
	ESCALAMIENTO("Escalamiento");
	
	public final String nombre;
	
	Funcion(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
	

}
