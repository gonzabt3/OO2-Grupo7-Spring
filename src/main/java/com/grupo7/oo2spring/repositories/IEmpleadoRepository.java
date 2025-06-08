package com.grupo7.oo2spring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.models.Empleado;

@Repository("empleadoRepository")
public interface IEmpleadoRepository extends JpaRepository<Empleado, Integer> {
	
	// Buscar empleado por email (único)
    Optional<Empleado> findByEmail(String email);
    
    // Buscar todos los empleados por apellido
    List<Empleado> findByApellido(String apellido);
	
	List<Empleado> findByArea(Area area);
	
	//Buscar empleados ordenados por nombre ascendente
    List<Empleado> findAllByOrderByNombreAsc();
	
	

}
