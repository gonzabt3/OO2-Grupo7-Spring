package com.grupo7.oo2spring.services;

import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;

import com.grupo7.oo2spring.models.Ticket;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.exception.TicketNoEncontradoException;
import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.dto.ControlDTO;
import com.grupo7.oo2spring.dto.EmpleadoDTO;
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
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.access.prepost.PreAuthorize;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;


@Service
@RequiredArgsConstructor
public class TicketService {

	private final ITicketRepository ticketRepository;

	private final IUsuarioRepository usuarioRepository;

	private final IControlRepository controlRepository;

	public Ticket getByIdTicket(int idTicket) {
		return ticketRepository.getByIdTicket(idTicket);
	}

	 public List<Ticket> findByAreaIsNull() {
	  return ticketRepository.findByArea(Area.SIN_ASIGNAR);
	 }
	        
	@Transactional(readOnly = true)
	public List<Ticket> findByUsuario(Usuario usuario) {
		return ticketRepository.findByUsuarioCreador(usuario);
	}

	@Transactional(readOnly = true)
	public List<Ticket> findByArea(Area area) {
		return ticketRepository.findByArea(area);
	}

	@Transactional
	public Ticket crearTicket(TicketDTO ticket, Usuario usuarioCreador) {
	    System.out.println("SERVICIO: Creando ticket con DTO: " + ticket);

	    Ticket nuevoTicket = new Ticket(ticket.getTitulo(), ticket.getDescripcion(),  usuarioCreador);
	    Ticket guardado = ticketRepository.save(nuevoTicket);
	    System.out.println("SERVICIO: Ticket guardado con ID: " + guardado.getIdTicket());
	    return guardado;
	}

	@PreAuthorize("hasRole('EMPLEADO')")
	@Transactional
	public void tomarTicketConControlInicial(int idTicket, Empleado empleadoLogueado, ControlDTO control)
			throws Exception {
		
		Ticket ticket = ticketRepository.getByIdTicket(idTicket);

		Control controlInicial = new Control();
		controlInicial.setTicket(ticket);
		controlInicial.setAccion(control.getAccion());
		controlInicial.setFechaEntrada(LocalDate.now());
		controlInicial.setFinalizado(false); // No está finalizado al tomarlo
		controlInicial.setFechaSalida(null);
		controlInicial.setFuncion(control.getFuncion());
		
		controlInicial.setEmpleado(empleadoLogueado);
		ticket.setEstado(Estado.ABIERTO);
		ticket.addControl(controlInicial);


		controlRepository.save(controlInicial);
		ticketRepository.save(ticket);
		System.out.println("Ticket #" + idTicket + " tomado exitosamente por " + empleadoLogueado.getNombre() + " "
				+ empleadoLogueado.getApellido());
	}
	
	 public Ticket buscarTicketPorId(int idTicket) throws TicketNoEncontradoException {
	        return ticketRepository.findById(idTicket)
	            .orElseThrow(() -> new TicketNoEncontradoException("Ticket con ID " + idTicket + " no encontrado"));
	    }
	
	 @Transactional
	 public void asignarAreaTicket(int idTicket, Area area) throws TicketNoEncontradoException {
	     Ticket ticket = ticketRepository.getByIdTicket(idTicket);

	     if (ticket == null) {
	         throw new TicketNoEncontradoException("No se encontró el ticket con ID " + idTicket);
	     }

	     if (area == null) {
	         throw new RuntimeException("El área a asignar no puede ser null.");
	     }

	     ticket.setArea(area);
	     ticketRepository.save(ticket);

	     System.out.println("Área '" + area.name() + "' asignada exitosamente al ticket #" + idTicket);
	 }
	
//	@Transactional(readOnly = true) // La transacción asegura que las relaciones lazy se carguen aquí
//    public TicketDTO getTicketDetailForView(int idTicket) {
//
//        Ticket ticket = ticketRepository.findById(idTicket) // Usa findById o el método con fetch
//                .orElseThrow(() -> new RuntimeException("Ticket no encontrado con ID: " + idTicket));
//
//        TicketDTO ticketDetailDTO = new TicketDTO();
//        ticketDetailDTO.setIdTicket(ticket.getIdTicket());
//        ticketDetailDTO.setTitulo(ticket.getTitulo());
//        ticketDetailDTO.setDescripcion(ticket.getDescripcion());
//        ticketDetailDTO.setFechaCreacion(ticket.getFechaCreacion());
//        ticketDetailDTO.setFechaCierre(ticket.getFechaCierre());
//        ticketDetailDTO.setEstado(ticket.getEstado());
//        ticketDetailDTO.setPrioridad(ticket.getPrioridad());
//        ticketDetailDTO.setArea(ticket.getArea());
//        ticketDetailDTO.setUsuarioCreador(ticket.getUsuarioCreador());
//
//        // Mapea la lista de entidades Control a ControlDTOs
//        ticketDetailDTO.setProcesos(ticket.getProcesos().stream().map(this::mapeoControlDTO).collect(Collectors.toList()));
//
//        return ticketDetailDTO;
//    }
//	private ControlDTO mapeoControlDTO(Control control) {
//        ControlDTO controlDTO = new ControlDTO();
//        controlDTO.setIdControl(control.getIdControl());
//        controlDTO.setFechaEntrada(control.getFechaEntrada());
//        controlDTO.setFechaSalida(control.getFechaSalida());
//        controlDTO.setAccion(control.getAccion());
//        controlDTO.setFinalizado(control.isFinalizado());
//        controlDTO.setEmpleado(control.getEmpleado());
//        controlDTO.setFuncion(control.getFuncion());
//        
//        return controlDTO;
//    }
//
//>>>>>>> refs/remotes/origin/pauche
	public List<Ticket> getTicketsByUsuario(int usuarioIdCreador) {
		return ticketRepository.findByUsuarioCreadorIdUsuario(usuarioIdCreador);
	}
	
	@Transactional
    public Ticket asignarPrioridad(int ticketId, Prioridad nuevaPrioridad) throws Exception {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new Exception("Error al recuperar datos del Ticket existente"));

        ticket.setPrioridad(nuevaPrioridad);
        return ticketRepository.save(ticket); // Guarda los cambios en la base de datos
    }
	 @Transactional
	    public Ticket asignarEstado(int ticketId, Estado nuevoEstado) throws Exception {
	        Ticket ticket = ticketRepository.findById(ticketId)
	                .orElseThrow(() -> new Exception("Error al recuperar datos del Ticket existente"));

	        ticket.setEstado(nuevoEstado);
	        return ticketRepository.save(ticket);
	 }
}
