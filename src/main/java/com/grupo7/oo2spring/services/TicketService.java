package com.grupo7.oo2spring.services;

import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo7.oo2spring.models.Ticket;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.exception.TicketNoEncontradoException;
import com.grupo7.oo2spring.models.Estado;
import com.grupo7.oo2spring.models.Prioridad;
import com.grupo7.oo2spring.repositories.ITicketRepository;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class TicketService {
	
	private final ITicketRepository ticketRepository;
	
	private final IUsuarioRepository usuarioRepository;
   
	public Ticket crearTicket(Usuario usuarioCreador, String titulo, String descripcion) {
	       Ticket nuevoTicket = new Ticket();
	       nuevoTicket.setUsuarioCreador(usuarioCreador);
	       nuevoTicket.setTitulo(titulo);
	       nuevoTicket.setDescripcion(descripcion);
	       nuevoTicket.setFechaCreacion(LocalDate.now());
	       nuevoTicket.setEstado(Estado.PENDIENTE); 
	       nuevoTicket.setPrioridad(Prioridad.MEDIA);

	       return ticketRepository.save(nuevoTicket);
   }
	
	 public Ticket buscarTicketPorId(int idTicket) throws TicketNoEncontradoException {
	        return ticketRepository.findById(idTicket)
	            .orElseThrow(() -> new TicketNoEncontradoException("Ticket con ID " + idTicket + " no encontrado"));
	    }
	
}
