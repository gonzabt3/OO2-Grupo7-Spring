package com.grupo7.oo2spring.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo7.oo2spring.models.Estado;
import com.grupo7.oo2spring.models.Prioridad;
import com.grupo7.oo2spring.models.Ticket;

@Repository
public interface ITicketRepository extends JpaRepository<Ticket, Integer> {
	
	public Optional<Ticket> findById(int idTicket);
	
	 // Buscar tickets por título
    List<Ticket> findByTituloContainingIgnoreCase(String titulo);
    
 // Buscar tickets por estado
    List<Ticket> findByEstado(Estado estado);
	
 // Buscar tickets por prioridad
    List<Ticket> findByPrioridad(Prioridad prioridad);
	
    // Buscar tickets creados por un usuario específico
    List<Ticket> findByUsuarioCreadorIdUsuario(int idUsuario);
    
    // Buscar tickets creados entre dos fechas
    List<Ticket> findByFechaCreacionBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    // Buscar tickets que estén abiertos (fechaCierre null)
    List<Ticket> findByFechaCierreIsNull();
    
    // Buscar tickets cerrados antes de una fecha
    List<Ticket> findByFechaCierreBefore(LocalDate fecha);
    
	

	

}
