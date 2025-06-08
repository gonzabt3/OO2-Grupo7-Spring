package com.grupo7.oo2spring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.models.Cliente;
import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Manager;
import com.grupo7.oo2spring.models.Rol;
import com.grupo7.oo2spring.models.Usuario;

@Repository("usuarioRepository")
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    Optional<Usuario> findByDni(String dni);
    Optional<Usuario> findByEmail(String email);
	 // Buscar usuario por nombre y apellido
    Optional<Usuario> findByNombreAndApellido(String nombre, String apellido);
	
 // Validar existencia de nombreUsuario (devuelve true si existe)
    boolean existsByNombreUsuario(String nombreUsuario);
    
    // Validar existencia de email
    boolean existsByEmail(String email);
    

    // Validar existencia de dni
    boolean existsByDni(String dni);
	Optional<Usuario> findByNombreUsuarioAndContraseña(String username, String password);
    
	
	List<Usuario> findByRolNot(Rol rol);
    
    // Buscar todos los empleados por apellido
    List<Empleado> findByApellido(String apellido);
	
	List<Empleado> findByArea(Area area);
	
	//Buscar empleados ordenados por nombre ascendente
    List<Empleado> findAllByOrderByNombreAsc();
	
	
    @Query("SELECT u FROM Usuario u WHERE u.rol = 'MANAGER'")
    List<Manager> findAllManagers();

    // Buscar manager por nombre de usuario
    @Query("SELECT u FROM Usuario u WHERE u.rol = 'MANAGER' AND u.nombreUsuario = :nombreUsuario")
    Optional<Usuario> findManagerByNombreUsuario(@Param("nombreUsuario") String nombreUsuario);

    // Buscar manager por email
    @Query("SELECT u FROM Usuario u WHERE u.rol = 'MANAGER' AND u.email = :email")
    Optional<Usuario> findManagerByEmail(@Param("email") String email);

    // Verificar si existe un manager con cierto nombre de usuario
    @Query("SELECT COUNT(u) > 0 FROM Manager u WHERE u.rol = 'MANAGER' AND u.nombreUsuario = :nombreUsuario")
    boolean existsManagerByNombreUsuario(@Param("nombreUsuario") String nombreUsuario);

    @Query("SELECT u FROM Empleado u WHERE u.idUsuario = :id AND u.rol = 'EMPLEADO'")
    Optional<Empleado> findEmpleadoById(@Param("id") int id);
//    
//    @Query("SELECT u FROM Empleado u WHERE u.nombreUsuario = :nombreUsuario AND u.rol = 'EMPLEADO'")
//    Optional<Empleado> findEmpleadoByNombre(@Param("nombreUsuario") String nombreUsuario);
//    
    @Query("SELECT u FROM Empleado u WHERE u.nombreUsuario = :nombreUsuario AND u.rol = 'EMPLEADO'")
    public Empleado findEmpleadoByNombre(@Param("nombreUsuario") String nombreUsuario);
    
    @Query("SELECT u FROM Empleado u WHERE u.rol = 'EMPLEADO'")
    List<Empleado> findAllEmpleados();


	

}
