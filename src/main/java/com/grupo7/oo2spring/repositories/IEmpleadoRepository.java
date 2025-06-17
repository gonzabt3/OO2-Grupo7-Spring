package com.grupo7.oo2spring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

	Optional<Empleado> findEmpleadoByIdEmpleado(int idEmpleado);

    @Query("SELECT u FROM Empleado u WHERE u.nombreUsuario = :nombreUsuario AND u.rol = 'EMPLEADO'")
    public Empleado findEmpleadoByNombreUsuario(@Param("nombreUsuario") String nombreUsuario);
    
    public Empleado findEmpleadoAllByNombreUsuario(@Param("nombreUsuario") String nombreUsuario); //Tambien busca a managers
    
    @Query("SELECT u FROM Empleado u WHERE u.rol = 'EMPLEADO'")
    List<Empleado> findAllEmpleados();
    
    @Query("SELECT u FROM Empleado u WHERE u.rol = 'MANAGER'")
    List<Empleado> findAllManagers();

    // Buscar manager por nombre de usuario
    @Query("SELECT u FROM Empleado u WHERE u.rol = 'MANAGER' AND u.nombreUsuario = :nombreUsuario")
    Optional<Empleado> findManagerByNombreUsuario(@Param("nombreUsuario") String nombreUsuario);

    // Buscar manager por email
    @Query("SELECT u FROM Empleado u WHERE u.rol = 'MANAGER' AND u.email = :email")
    Optional<Empleado> findManagerByEmail(@Param("email") String email);

    // Verificar si existe un manager con cierto nombre de usuario
    @Query("SELECT COUNT(u) > 0 FROM Empleado u WHERE u.rol = 'MANAGER' AND u.nombreUsuario = :nombreUsuario")
    boolean existsManagerByNombreUsuario(@Param("nombreUsuario") String nombreUsuario);

	

}
