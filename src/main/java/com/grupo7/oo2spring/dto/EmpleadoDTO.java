package com.grupo7.oo2spring.dto;

import java.util.Objects;

import com.grupo7.oo2spring.models.Area;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmpleadoDTO extends PersonaDTO {

	private int idEmpleado;
	 
	private Area area;
	

	private boolean disponibilidad;
	private int nivelAcceso;
	
	public EmpleadoDTO(String nombre, String apellido, String dni, String email, Area area, boolean disponibilidad,
			int nivelAcceso) {
		super(nombre, apellido, dni, email);
		this.area = area;
		this.disponibilidad = disponibilidad;
		this.nivelAcceso = nivelAcceso;
	}

	
	public int getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public boolean isDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(boolean disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public int getNivelAcceso() {
		return nivelAcceso;
	}

	public void setNivelAcceso(int nivelAcceso) {
		this.nivelAcceso = nivelAcceso;
	}

	@Override
	public String toString() {
		return "Empleado [area=" + area + ", disponibilidad=" + disponibilidad + ", nivelAcceso=" + nivelAcceso + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(area, disponibilidad, idEmpleado, nivelAcceso);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmpleadoDTO other = (EmpleadoDTO) obj;
		return area == other.area && disponibilidad == other.disponibilidad && idEmpleado == other.idEmpleado
				&& nivelAcceso == other.nivelAcceso;
	}
	
	
	
	

}
