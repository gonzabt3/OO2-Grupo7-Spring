package com.grupo7.oo2spring.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.models.Estado;
import com.grupo7.oo2spring.models.Prioridad;
import com.grupo7.oo2spring.models.Ticket;
import com.grupo7.oo2spring.models.Usuario;

@Repository
public interface ITicketRepository extends JpaRepository<Ticket, Integer> {
	
	public Optional<Ticket> findById(int idTicket);
	
	public Ticket getByIdTicket(int idTicket);
	 // Buscar tickets por título
    List<Ticket> findByTituloContainingIgnoreCase(String titulo);
    
 // Buscar tickets por estado
    List<Ticket> findByEstado(Estado estado);
	
 // Buscar tickets por prioridad
    List<Ticket> findByPrioridad(Prioridad prioridad);
	
    // Buscar tickets creados por un usuario específico
<<<<<<< HEAD
    List<Ticket> findByUsuarioCreador(Usuario usuarioCreador);
=======
    List<Ticket> findByUsuarioCreadorIdUsuario(int usuarioCreadorId);
>>>>>>> 3217439d5e3859a19e16b86697739d52f4a8684f
    
    // Buscar tickets creados entre dos fechas
    List<Ticket> findByFechaCreacionBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    // Buscar tickets que estén abiertos (fechaCierre null)
    List<Ticket> findByFechaCierreIsNull();
    
    // Buscar tickets cerrados antes de una fecha
    List<Ticket> findByFechaCierreBefore(LocalDate fecha);
    
	// Busca los tickets sin area
    List<Ticket> findByAreaIsNull();
	
    List<Ticket> findByArea(Area area);
	

}
