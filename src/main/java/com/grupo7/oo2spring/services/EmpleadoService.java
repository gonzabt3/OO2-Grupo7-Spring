package com.grupo7.oo2spring.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.grupo7.oo2spring.enums.TipoRol;
import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Rol;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.IEmpleadoRepository;
import com.grupo7.oo2spring.repositories.IRolRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpleadoService {
	
	 private final IEmpleadoRepository empleadoRepository;
	 private final IRolRepository rolRepository;
	 
	 public Empleado crearEmpleado(String nombre, String apellido, String dni, String email, String nombreUsuario, String contraseña, Area area, boolean disponibilidad) throws Exception {
	        Empleado u = new Empleado(nombre, apellido, dni, email, nombreUsuario, contraseña, area, disponibilidad);
	        Rol rolEmpleado = rolRepository.findByTipo(TipoRol.EMPLEADO);
	        u.setRol(rolEmpleado);
	        return u;
	    }
	    
	 
	 public Optional<Empleado> findByEmpleado(int idEmpleado) {
		 return empleadoRepository.findEmpleadoById(idEmpleado);
	 }
	 
	 public Empleado findByEmpleadoNombre(String nombreEmpleado) {
		 return empleadoRepository.findEmpleadoByNombreUsuario(nombreEmpleado);
	 }
	
	public Empleado guardarEmpleado(Empleado empleado) {
        // Aquí podés hacer validaciones adicionales si querés

        // Guarda o actualiza el empleado en la base
        return empleadoRepository.save(empleado);
    }
	
	public Optional<Empleado> buscarPorId(int idEmpleado) {
		return empleadoRepository.findEmpleadoById(idEmpleado);
	}

}
