package com.grupo7.oo2spring.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo7.oo2spring.models.Estado;
import com.grupo7.oo2spring.models.Prioridad;
import com.grupo7.oo2spring.models.Ticket;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.ITicketRepository;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;

@Service
public class TicketService {
	
	@Autowired
	private ITicketRepository ticketRepository;
	
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	public Optional<Ticket> findByTicketId(int idTicket){
		return ticketRepository.findById(idTicket);
	};
	
	public List<Ticket> listatickets(){
		return ticketRepository.findAll();
	}
	
	 // Buscar tickets por título
   List<Ticket> findByTituloContainingIgnoreCase(String titulo){
	   return ticketRepository.findByTituloContainingIgnoreCase(titulo);
   };
   
// Buscar tickets por estado
   List<Ticket> findByEstado(Estado estado){
	   return ticketRepository.findByEstado(estado);
   };
	
// Buscar tickets por prioridad
   List<Ticket> findByPrioridad(Prioridad prioridad){
	   return ticketRepository.findByPrioridad(prioridad);
   };
	
   // Buscar tickets creados por un usuario específico
   List<Ticket> findByUsuarioCreadorIdUsuario(int idUsuario){
	   return ticketRepository.findByUsuarioCreadorIdUsuario(idUsuario);
   };
   
   // Buscar tickets creados entre dos fechas
   List<Ticket> findByFechaCreacionBetween(LocalDate fechaInicio, LocalDate fechaFin){
	   return ticketRepository.findByFechaCreacionBetween(fechaInicio, fechaFin);
   };
   
   // Buscar tickets que estén abiertos (fechaCierre null)
   List<Ticket> findByFechaCierreIsNull(){
	   return ticketRepository.findByFechaCierreIsNull();
   };
   
   // Buscar tickets cerrados antes de una fecha
   List<Ticket> findByFechaCierreBefore(LocalDate fecha){
	   return ticketRepository.findByFechaCierreBefore(fecha);
   };
   
 
   public Ticket crearTicket(Usuario usuarioCreador, String titulo, String descripcion) {
       Ticket nuevoTicket = new Ticket();
       nuevoTicket.setUsuarioCreador(usuarioCreador);
       nuevoTicket.setTitulo(titulo);
       nuevoTicket.setDescripcion(descripcion);
       nuevoTicket.setFechaCreacion(LocalDate.now());
       nuevoTicket.setEstado(Estado.PENDIENTE); // Usando String directamente, si es enum, usa el valor del enum
       // nuevoTicket.setPrioridad(Prioridad.NORMAL); // Si tienes un enum Prioridad
       nuevoTicket.setPrioridad(Prioridad.MEDIA); // Si tienes un enum Estado

       return ticketRepository.save(nuevoTicket);
   }
	
}
