package com.grupo7.oo2spring.services;

import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo7.oo2spring.models.Ticket;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.exception.TicketNoEncontradoException;
import com.grupo7.oo2spring.dto.ControlDTO;
import com.grupo7.oo2spring.dto.TicketDTO;
import com.grupo7.oo2spring.models.Control;
import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Estado;
import com.grupo7.oo2spring.models.Prioridad;
import com.grupo7.oo2spring.repositories.IControlRepository;
import com.grupo7.oo2spring.repositories.ITicketRepository;

import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.grupo7.oo2spring.models.Estado;
import com.grupo7.oo2spring.models.Prioridad;
import com.grupo7.oo2spring.models.Ticket;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.ITicketRepository;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class TicketService {
	
	private final ITicketRepository ticketRepository;
	
	private final IUsuarioRepository usuarioRepository;
	
	private final IControlRepository controlRepository;
	
	public Ticket getByIdTicket(int idTicket) {
		return ticketRepository.getByIdTicket(idTicket);
	}
   
	public Ticket crearTicket(TicketDTO ticket, Usuario usuariocreador) {
	       Ticket nuevoTicket = new Ticket();
	       nuevoTicket.setUsuarioCreador(usuariocreador);
	       nuevoTicket.setTitulo(ticket.getTitulo());
	       nuevoTicket.setDescripcion(ticket.getDescripcion());
	       nuevoTicket.setFechaCreacion(LocalDate.now());
	       nuevoTicket.setEstado(Estado.PENDIENTE); 
	       nuevoTicket.setPrioridad(Prioridad.MEDIA);
	       nuevoTicket.setArea(null);
	       return ticketRepository.save(nuevoTicket);
	   }
	
	
    //@PreAuthorize("hasRole('EMPLEADO')")
	@Transactional
    public void tomarTicketConControlInicial(int idTicket, Usuario usuarioCreador, ControlDTO control) throws Exception {
		Ticket ticket = ticketRepository.getByIdTicket(idTicket); // Obtener la entidad Ticket

        Empleado empleado = new Empleado("empleadotest", "apeld", "99999999", "email@gmail.com", "empl", "laweaaa", null, false);
//        // Validaciones de negocio:
//        if (ticket.getArea() == null) {
//            throw new RuntimeException("El ticket #" + idTicket + " no tiene un área asignada. No se puede tomar.");
//        }
//        if (empleado.getArea() == null || !empleado.getArea().equals(ticket.getArea())) {
//            throw new RuntimeException("No puedes tomar este ticket. No perteneces al área '" + ticket.getArea().name() + "'.");
//        }
//        if (ticket.getEstado() == Estado.CERRADO || ticket.getEstado() == Estado.RESUELTO) {
//             throw new RuntimeException("El ticket #" + idTicket + " ya está finalizado (" + ticket.getEstado().name() + "). No se puede tomar.");
//        }
//        if (ticket.getEstado() == Estado.EN_PROGRESO) {
//            throw new RuntimeException("El ticket #" + idTicket + " ya está en progreso.");
//        }

        
        // Crear la primera entrada de Control
        Control controlInicial = new Control();
        controlInicial.setTicket(ticket);
       // controlInicial.setEmpleado((Empleado) usuarioCreador);
        
        controlInicial.setAccion(control.getAccion());
        controlInicial.setFechaEntrada(LocalDate.now());
        controlInicial.setFinalizado(false); // No está finalizado al tomarlo
        controlInicial.setFechaSalida(null);
        ticket.setEstado(Estado.ABIERTO);
        ticket.addControl(controlInicial); // Añadir a la lista de procesos del ticket

        // Cambiar el estado del Ticket a EN_PROGRESO
        
        
        controlRepository.save(controlInicial); // Persistir el Control
        ticketRepository.save(ticket); // Persistir los cambios en el Ticket
        System.out.println("Ticket #" + idTicket + " tomado exitosamente por " + usuarioCreador.getNombre() + " " + usuarioCreador.getApellido());
    }

	//@PreAuthorize("hasRole('EMPLEADO')")
	@Transactional
    public void agregarControlATicket(int idTicket, Empleado empleado, ControlDTO control, boolean finalizaTicket) throws Exception {
        Ticket ticket = getByIdTicket(idTicket);
//        
        Empleado emplea = new Empleado("empleadotest", "apeld", "99999999", "email@gmail.com", "empl", "laweaaa", null, false);
//                .orElseThrow(() -> new RuntimeException("Empleado no encontrado: " + nombreUsuarioEmpleado));
//
//        // Validaciones para añadir control
//        if (ticket.getArea() == null || !ticket.getArea().equals(empleado.getArea())) {
//            throw new RuntimeException("Este ticket no está asignado a tu área o no tienes permisos.");
//        }
//        if (ticket.getEstado() == Estado.CERRADO || ticket.getEstado() == Estado.RESUELTO) {
//            throw new RuntimeException("No se puede añadir un control a un ticket ya finalizado.");
//        }

        Control nuevoControl = new Control();
        nuevoControl.setAccion(control.getAccion());
        //nuevoControl.setEmpleado(emplea);
        nuevoControl.setTicket(ticket);
        nuevoControl.setFechaEntrada(LocalDate.now());
        nuevoControl.setFinalizado(finalizaTicket);

        ticket.addControl(nuevoControl);

        if (finalizaTicket) {
            nuevoControl.setFechaSalida(LocalDate.now());
            ticket.setFechaCierre(LocalDate.now());
            ticket.setEstado(Estado.RESUELTO); // O Estado.CERRADO, según tu flujo
        }

        controlRepository.save(nuevoControl);
        ticketRepository.save(ticket);
    }
	
	 public Ticket buscarTicketPorId(int idTicket) throws TicketNoEncontradoException {
	        return ticketRepository.findById(idTicket)
	            .orElseThrow(() -> new TicketNoEncontradoException("Ticket con ID " + idTicket + " no encontrado"));
	    }
	
}
