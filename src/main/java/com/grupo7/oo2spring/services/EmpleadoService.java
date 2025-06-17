package com.grupo7.oo2spring.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Rol;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.IEmpleadoRepository;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpleadoService {
	
	 private final IEmpleadoRepository empleadoRepository;
	 
	 public Optional<Empleado> findByEmpleado(int idEmpleado) {
		 return empleadoRepository.findEmpleadoByIdEmpleado(idEmpleado);
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
		return empleadoRepository.findEmpleadoByIdEmpleado(idEmpleado);
	}

}
