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
public class Empleado extends Usuario {
		 

	@Enumerated(EnumType.STRING)
	private Area area;
	
	private boolean disponibilidad;
	
	public Empleado(String nombre, String apellido, String dni, String email,String nombreUsuario, String password, Area area, boolean disponibilidad) throws Exception {
		super(nombre, apellido, dni, email, nombreUsuario, password);
		this.area = area;
		this.disponibilidad = disponibilidad;
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

	@Override
	public String toString() {
		return "Empleado [area=" + area + ", disponibilidad=" + disponibilidad + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(area, disponibilidad);
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
		return area == other.area && disponibilidad == other.disponibilidad;
	}	


}
