package com.grupo7.oo2spring.models;


public enum Area {
	SOPORTE("Soporte"),
    VENTAS("Ventas"),
    DESARROLLO("Desarrollo"),
    FINANZAS("Finanzas"),
    RECURSOS_HUMANOS("Recursos Humanos"),
    MARKETING("Marketing"),
	SIN_ASIGNAR("Sin Asignar");
    
    public final String nombre;
	
    Area(String nombre) {
    	this.nombre = nombre;
    }
    
    public String getNombre() {
		return nombre;
	}
    
    
}