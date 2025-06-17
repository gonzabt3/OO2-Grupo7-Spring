package com.grupo7.oo2spring.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo7.oo2spring.models.MensajeContacto;
import com.grupo7.oo2spring.models.Usuario;

@Repository("mensajeRepository")
public interface IMensajeRepository extends JpaRepository<MensajeContacto, Integer>  {
	
	 // Buscar todos los mensajes por un usuario específico
    List<MensajeContacto> findByUsuario(Usuario usuario);

    // Buscar mensajes que contengan cierta palabra clave
    List<MensajeContacto> findByMensajeContainingIgnoreCase(String palabraClave);

    // Buscar los mensajes más recientes primero
    List<MensajeContacto> findAllByOrderByFechaEnvioDesc();

    // Buscar por rango de fechas
    List<MensajeContacto> findByFechaEnvioBetween(LocalDateTime desde, LocalDateTime hasta);
	

}
