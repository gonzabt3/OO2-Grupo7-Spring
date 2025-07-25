package com.grupo7.oo2spring.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo7.oo2spring.models.UsuarioBase;

@Repository("usuarioBaseRepository")
public interface IUsuarioBaseRepository extends JpaRepository<UsuarioBase, Integer> {
	 Optional<UsuarioBase> findByNombreUsuario(String nombreUsuario);
}
