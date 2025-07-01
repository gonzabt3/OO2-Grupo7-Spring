package com.grupo7.oo2spring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Rol;
import com.grupo7.oo2spring.models.Usuario;

@Repository("usuarioRepository")
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	Usuario findByNombreUsuario(String nombreUsuario);
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
    
	




	

}
