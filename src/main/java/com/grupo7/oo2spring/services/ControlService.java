package com.grupo7.oo2spring.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grupo7.oo2spring.dto.ControlDTO;
import com.grupo7.oo2spring.dto.TicketDTO;
import com.grupo7.oo2spring.exception.TicketNoEncontradoException;
import com.grupo7.oo2spring.models.Control;
import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Estado;
import com.grupo7.oo2spring.models.Funcion;
import com.grupo7.oo2spring.models.Prioridad;
import com.grupo7.oo2spring.models.Ticket;
import com.grupo7.oo2spring.repositories.IAreaRepository;
import com.grupo7.oo2spring.repositories.IControlRepository;
import com.grupo7.oo2spring.repositories.ITicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ControlService {
	private final IControlRepository controlRepository;
	private final ITicketRepository ticketRepository;
	private final TicketService ticketService;
	
	
	public Optional<Control> buscarControlPorId(int controlID){
		return controlRepository.findById(controlID);
	}
	

	private ControlDTO convertToControlDTO(Control control) {
		String empleadoNombreCompleto = null;
        if (control.getEmpleado() != null) {
            empleadoNombreCompleto = control.getEmpleado().getNombre() + " " + control.getEmpleado().getApellido();
        }

        String tituloTicket = null;
        if (control.getTicket() != null) {
            tituloTicket = control.getTicket().getTitulo();
        }

        return new ControlDTO(
                control.getIdControl(),
                control.getTicket().getIdTicket(),
                control.getAccion(),
                control.getFuncion() != null ? control.getFuncion().name() : null,
                control.isFinalizado(),
                control.getFechaEntrada(),
                control.getFechaSalida(),
                empleadoNombreCompleto,
                tituloTicket
        );
    }
	
	public Control revisionDeUltimoControl(Ticket ticket) {
		int idUltimo=1;
		if(!ticket.getProcesos().isEmpty()) {
			idUltimo=ticket.getProcesos().get(ticket.getProcesos().size()-1).getIdControl()+1;
		}
		Control ultimoControl = ticket.getProcesos().get(idUltimo);
		return ultimoControl;
	}
	
	public boolean existeControlPendiente(Ticket ticket) {
		return ticket.getProcesos().stream().anyMatch(control -> !control.isFinalizado());
	}
	
	@Transactional
    public ControlDTO tomarTicket(int idTicket, Empleado empleadoLogueado)
            throws Exception, TicketNoEncontradoException {

        Ticket ticket = ticketService.buscarTicketPorId(idTicket);
        if (ticket == null) {
            throw new TicketNoEncontradoException("Ticket no encontrado con ID: " + idTicket);
        }

        if (existeControlPendiente(ticket)) {
            throw new Exception("Ya existe una intervención en curso para este ticket.");
        }

        Control controlInicial = new Control();
        controlInicial.setTicket(ticket);
        controlInicial.setEmpleado(empleadoLogueado);

        controlInicial.setAccion("Ticket tomado. Inicio de la intervención.");
        controlInicial.setFuncion(Funcion.SEGUIMIENTO); 
        controlInicial.setFinalizado(false); 
        controlInicial.setFechaEntrada(LocalDateTime.now()); 
        controlInicial.setFechaSalida(null);

        ticket.addControl(controlInicial);

        
        ticket.setEstado(Estado.ABIERTO); // El ticket pasa a ABIERTO
        

        controlRepository.save(controlInicial);
        ticketRepository.save(ticket);

        System.out.println("Ticket #" + idTicket + " tomado exitosamente por " + empleadoLogueado.getNombre() + " "
                + empleadoLogueado.getApellido() + " (Control inicial creado).");

        return convertToControlDTO(controlInicial);
    }
	
	@Transactional
	public ControlDTO ControlInicial(int idTicket, Empleado empleadoLogueado, ControlDTO control)
			throws Exception, TicketNoEncontradoException {
		
		Ticket ticket = ticketService.buscarTicketPorId(idTicket);
		if(existeControlPendiente(ticket)) {
			throw new Exception("Existe un control pendiente");
		}
		if (ticket.getEstado() == Estado.PENDIENTE) {
            throw new IllegalStateException("No se puede crear un control para un ticket en estado PENDIENTE. Primero debe ser tomado.");
        }
		Control controlInicial = new Control();
		controlInicial.setTicket(ticket);
		controlInicial.setAccion(control.accion());
		controlInicial.setFechaEntrada(LocalDateTime.now());
		controlInicial.setFinalizado(control.finalizado()); // No está finalizado al tomarlo
		controlInicial.setFechaSalida(null);
		controlInicial.setFuncion(Funcion.valueOf(control.funcion().toUpperCase()));
		controlInicial.setEmpleado(empleadoLogueado);
		ticket.addControl(controlInicial);
		if (control.finalizado()) {
			controlInicial.setFinalizado(true);
			controlInicial.setFechaSalida(LocalDateTime.now());
			ticket.setFechaCierre(LocalDate.now()); // El ticket se cierra/resuelve
			ticket.setEstado(Estado.RESUELTO);
		} else {
			// Si el ticket estaba pendiente, ahora está en proceso
			if (ticket.getEstado() == Estado.PENDIENTE) {
				ticket.setEstado(Estado.ABIERTO);
			}
		}
		controlRepository.save(controlInicial);
		ticketRepository.save(ticket);
		System.out.println("Ticket #" + idTicket + " tomado exitosamente por " + empleadoLogueado.getNombre() + " "
				+ empleadoLogueado.getApellido());
		return convertToControlDTO(controlInicial);
	}
	
	public ControlDTO procesarEdicionTicket(ControlDTO control, int controlID) throws TicketNoEncontradoException {
		Control controlEdicion = controlRepository.findById(controlID)
				.orElseThrow(()-> new TicketNoEncontradoException("Control no encontrado: " + controlID));
		
		controlEdicion.setFuncion(Funcion.valueOf(control.funcion().toUpperCase()));
		controlEdicion.setAccion(control.accion());
		if(control.finalizado()) {
			controlEdicion.setFinalizado(true);
			controlEdicion.setFechaSalida(LocalDateTime.now());
			Ticket ticket = controlEdicion.getTicket();
			ticket.setFechaCierre(LocalDate.now());
			ticket.setEstado(Estado.RESUELTO);
			ticketRepository.save(ticket);
		}else {
			controlEdicion.setFinalizado(false);
		}
		controlRepository.save(controlEdicion);
		return convertToControlDTO(controlEdicion);
	}

}
