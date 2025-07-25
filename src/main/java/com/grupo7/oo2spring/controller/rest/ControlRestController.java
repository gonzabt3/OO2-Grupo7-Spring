package com.grupo7.oo2spring.controller.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grupo7.oo2spring.dto.ControlDTO;
import com.grupo7.oo2spring.dto.EmpleadoDTO;
import com.grupo7.oo2spring.dto.TicketDTO;
import com.grupo7.oo2spring.exception.TicketNoEncontradoException;
import com.grupo7.oo2spring.models.Control;
import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Funcion;
import com.grupo7.oo2spring.models.Ticket;
import com.grupo7.oo2spring.repositories.IEmpleadoRepository;
import com.grupo7.oo2spring.repositories.ITicketRepository;
import com.grupo7.oo2spring.services.ControlService;
import com.grupo7.oo2spring.services.EmpleadoService;
import com.grupo7.oo2spring.services.TicketService;
import com.grupo7.oo2spring.services.UsuarioService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/control")
@RequiredArgsConstructor
public class ControlRestController {

	private final ITicketRepository ticketRepository;
	private final IEmpleadoRepository empleadoRepository;
	private final EmpleadoService empleadoService;
	private final TicketService ticketService;
	private final ControlService controlService;
	
	private TicketDTO convertToTicketDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setIdTicket(ticket.getIdTicket());
        dto.setTitulo(ticket.getTitulo());
        dto.setEstado(ticket.getEstado());
        dto.setFechaCreacion(ticket.getFechaCreacion());
        dto.setFechaCierre(ticket.getFechaCierre());
        dto.setArea(ticket.getArea());
        return dto;
    }

    private ControlDTO convertToControlDTO(Control control) {
        ControlDTO dto = new ControlDTO();
        dto.setIdControl(control.getIdControl());
        dto.setAccion(control.getAccion());
        dto.setFuncion(control.getFuncion()); 
        dto.setFinalizado(control.isFinalizado());
        dto.setFechaEntrada(control.getFechaEntrada());
        dto.setFechaSalida(control.getFechaSalida());
        if (control.getEmpleado() != null) {
        	dto.setEmpleado(control.getEmpleado() != null ? control.getEmpleado().getNombre() + " " + control.getEmpleado().getApellido() : null);
        }
        dto.setTituloTicket(control.getTicket().getTitulo());
        return dto;
    }
	
	@PreAuthorize("hasAnyRole('MANAGER', 'EMPLEADO')")
    @GetMapping("/listado-controles/{idTicket}")
    public ResponseEntity<Map<String, Object>> mostrarListaControles(@PathVariable int idTicket, @AuthenticationPrincipal UserDetails usuariolog) throws TicketNoEncontradoException {
		Ticket ticket = ticketService.buscarTicketPorId(idTicket);
        TicketDTO ticketDTO = convertToTicketDTO(ticket);
        List<ControlDTO> controlsDTO = ticket.getProcesos().stream().map(this::convertToControlDTO).collect(Collectors.toList());
        Map<String, Object> response = Map.of(
                "ticket", ticketDTO,
                "controles", controlsDTO
            );
        return ResponseEntity.ok(response);
	}
	
	@PreAuthorize("hasAnyRole('MANAGER', 'EMPLEADO')")
    @PostMapping("/{idTicket}/nuevo")
    public ResponseEntity<ControlDTO> procesaCreacionControl(
            @PathVariable int idTicket,
            @Valid @RequestBody ControlDTO controlDTO,
            @AuthenticationPrincipal UserDetails usuariolog,
            RedirectAttributes redirectAttributes) throws Exception {

        Empleado empleadoLogeado = empleadoService.findByEmpleadoNombre(usuariolog.getUsername());
        try {
            ControlDTO controlCreado = controlService.ControlInicial(idTicket, empleadoLogeado, controlDTO);
            return new ResponseEntity<>(controlCreado, HttpStatus.CREATED);

        } catch (TicketNoEncontradoException  e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
	
	@PreAuthorize("hasAnyRole('MANAGER', 'EMPLEADO')")
	@GetMapping("/{idTicket}/ControlPendiente")
	public ResponseEntity<?> controlPendiente(@PathVariable int idTicket) throws TicketNoEncontradoException{
		Ticket ticket = ticketService.buscarTicketPorId(idTicket);
		boolean pendiente = controlService.existeControlPendiente(ticket);
		return ResponseEntity.ok(pendiente);
	}
	
	
	@PreAuthorize("hasAnyRole('MANAGER', 'EMPLEADO')")
	@GetMapping("/detalle/{idControl}")
	public ResponseEntity<ControlDTO> obtenerControl(@PathVariable int idControl) throws TicketNoEncontradoException {
	    Control control = controlService.buscarControlPorId(idControl).orElseThrow(()-> new TicketNoEncontradoException("Ticket no encontrado"));
	    if (control == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok(convertToControlDTO(control)); 
	}
	
	@PreAuthorize("hasAnyRole('MANAGER', 'EMPLEADO')")
	@GetMapping("/detalleTicket/{idTicket}")
	public ResponseEntity<TicketDTO> obtenerTicket(@PathVariable int idTicket) throws TicketNoEncontradoException {
	    Ticket ticket = ticketService.buscarTicketPorId(idTicket);
	    if (ticket == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok(convertToTicketDTO(ticket));
	}
	
	@PreAuthorize("hasAnyRole('MANAGER', 'EMPLEADO')")
	@PutMapping("/{idTicket}/edicion/{idControl}")
	public ResponseEntity<ControlDTO> actualizarControl(@PathVariable int idTicket,
									@PathVariable int idControl,
									@Valid @RequestBody ControlDTO control,
                                    @AuthenticationPrincipal UserDetails usuariolog) throws Exception {
		System.out.println("ENTRO AL POST DE EDITAR");
		ControlDTO controlActualizado = controlService.procesarEdicionTicket(control,idControl);
		return ResponseEntity.ok(controlActualizado);
	}
}
