package com.grupo7.oo2spring.dto;

import java.util.Objects;

import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.models.Rol;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmpleadoDTO extends UsuarioDTO {

	private int idEmpleado;
	 
	private Area area;
	
	private Rol rol;
	
	private boolean disponibilidad;
	
	public EmpleadoDTO(String nombre, String apellido, String dni, String email,String nombreUsuario, String contraseña, Area area, boolean disponibilidad) throws Exception {
		super(nombre, apellido, dni, email,
			       nombreUsuario, contraseña);
		this.area = area;
		this.disponibilidad = disponibilidad;
		this.rol = Rol.EMPLEADO;
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

	@Override
	public String toString() {
		return "Empleado [area=" + area + ", disponibilidad=" + disponibilidad + ", nivelAcceso=" + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(area, disponibilidad, idEmpleado);
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
		return area == other.area && disponibilidad == other.disponibilidad && idEmpleado == other.idEmpleado;
	}
	
	
	
	

}
