package com.grupo7.oo2spring.models;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Empleado extends Persona {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idEmpleado;
	 

	@Enumerated(EnumType.STRING)
	private Area area;
	
	@Enumerated(EnumType.STRING)
	private Rol rol;
	

	private boolean disponibilidad;
	private int nivelAcceso;
	
	public Empleado(String nombre, String apellido, String dni, String email, Area area, boolean disponibilidad,
			int nivelAcceso) {
		super(nombre, apellido, dni, email);
		this.area = area;
		this.disponibilidad = disponibilidad;
		this.nivelAcceso = nivelAcceso;
		this.rol = Rol.EMPLEADO;
	}

	
	public int getIdEmpleado() {
		return idEmpleado;
	}

	private void setIdEmpleado(int idEmpleado) {
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
		Empleado other = (Empleado) obj;
		return area == other.area && disponibilidad == other.disponibilidad && idEmpleado == other.idEmpleado
				&& nivelAcceso == other.nivelAcceso;
	}
	
	
	
	

}
