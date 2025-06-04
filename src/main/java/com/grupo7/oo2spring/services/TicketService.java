package com.grupo7.oo2spring.services;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo7.oo2spring.models.Ticket;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.models.Estado;
import com.grupo7.oo2spring.models.Prioridad;
import com.grupo7.oo2spring.repositories.ITicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {
	
	    private final ITicketRepository ticketRepository;

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
}