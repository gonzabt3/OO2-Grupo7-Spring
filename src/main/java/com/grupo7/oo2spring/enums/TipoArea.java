package com.grupo7.oo2spring.enums;

public enum TipoArea {
	SOPORTE("Soporte"),
    VENTAS("Ventas"),
    DESARROLLO("Desarrollo"),
    FINANZAS("Finanzas"),
    RECURSOS_HUMANOS("Recursos Humanos"),
    MARKETING("Marketing"),
	SIN_ASIGNAR("Sin Asignar");


public final String nombre;

TipoArea(String nombre) {
	this.nombre = nombre;
}

public String getNombre() {
	return nombre;
}

}