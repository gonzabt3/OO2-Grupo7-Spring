package com.grupo7.oo2spring.controller.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grupo7.oo2spring.dto.ControlDTO;
import com.grupo7.oo2spring.dto.TicketDTO;
import com.grupo7.oo2spring.exception.TicketNoEncontradoException;
import com.grupo7.oo2spring.models.Control;
import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Funcion;
import com.grupo7.oo2spring.models.Ticket;
import com.grupo7.oo2spring.models.UsuarioBase;
import com.grupo7.oo2spring.services.ControlService;
import com.grupo7.oo2spring.services.EmailService;
import com.grupo7.oo2spring.services.EmpleadoService;
import com.grupo7.oo2spring.services.TicketService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/control")
@RequiredArgsConstructor
@Tag(name = "Controles", description = "Operaciones de gestión de intervenciones y controles de tickets")
public class ControlRestController {
	private final EmpleadoService empleadoService;
	private final TicketService ticketService;
	private final ControlService controlService;
	private final EmailService emailService;

	private TicketDTO convertirTicketDTO(Ticket ticket) {
		TicketDTO dto = new TicketDTO();
		dto.setIdTicket(ticket.getIdTicket());
		dto.setTitulo(ticket.getTitulo());
		dto.setEstado(ticket.getEstado());
		dto.setFechaCreacion(ticket.getFechaCreacion());
		dto.setFechaCierre(ticket.getFechaCierre());
		dto.setArea(ticket.getArea());
		return dto;
	}

	private ControlDTO convertirControlDTO(Control control) {
		String empleadoNombreCompleto = null;
		if (control.getEmpleado() != null) {
			empleadoNombreCompleto = control.getEmpleado().getNombre() + " " + control.getEmpleado().getApellido();
		}

		String tituloTicket = null;
		if (control.getTicket() != null) {
			tituloTicket = control.getTicket().getTitulo();
		}

		return new ControlDTO(control.getIdControl(), control.getTicket().getIdTicket(), control.getAccion(),
				control.getFuncion() != null ? control.getFuncion().name() : null, control.isFinalizado(),
				control.getFechaEntrada(), control.getFechaSalida(), empleadoNombreCompleto, tituloTicket);
	}

	@Operation(summary = "Obtener lista de controles para un ticket", description = "Recupera todos los controles (intervenciones) asociados a un ticket específico.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "404", description = "Ticket no encontrado", content = @Content(mediaType = "text/plain")) })
	@PreAuthorize("hasAnyRole('MANAGER', 'EMPLEADO')")
	@GetMapping("/listado-controles/{idTicket}")
	public ResponseEntity<Map<String, Object>> mostrarListaControles(
			@Parameter(description = "ID del ticket para el cual se quieren listar los controles") @PathVariable int idTicket,
			@AuthenticationPrincipal UserDetails usuariolog) throws TicketNoEncontradoException {
		Ticket ticket = ticketService.buscarTicketPorId(idTicket);
		TicketDTO ticketDTO = convertirTicketDTO(ticket);
		List<ControlDTO> controlsDTO = ticket.getProcesos().stream().map(this::convertirControlDTO)
				.collect(Collectors.toList());
		Map<String, Object> response = Map.of("ticket", ticketDTO, "controles", controlsDTO,"estadoTicket", ticket.getEstado().name());
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Crear un nuevo control para un ticket", description = "Registra una nueva intervención (control) para un ticket. Un ticket solo puede tener un control activo a la vez.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Control creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ControlDTO.class))),
			@ApiResponse(responseCode = "409", description = "Conflicto: Ya existe un control pendiente para este ticket", content = @Content(mediaType = "text/plain")) })
	@PreAuthorize("hasAnyRole('MANAGER', 'EMPLEADO')")
	@PostMapping("/{idTicket}/nuevo")
	public ResponseEntity<ControlDTO> procesaCreacionControl(
			@Parameter(description = "ID del ticket al que se asociará el nuevo control") @PathVariable int idTicket,
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del nuevo control a crear", required = true, content = @Content(schema = @Schema(implementation = ControlDTO.class))) @Valid @RequestBody ControlDTO controlDTO,
			@AuthenticationPrincipal UserDetails usuariolog, RedirectAttributes redirectAttributes) throws Exception {

		Empleado empleadoLogeado = empleadoService.findByEmpleadoNombre(usuariolog.getUsername());
		try {
			ControlDTO controlCreado = controlService.ControlInicial(idTicket, empleadoLogeado, controlDTO);
			return new ResponseEntity<>(controlCreado, HttpStatus.CREATED);

		} catch (TicketNoEncontradoException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} 
	}

	@Operation(summary = "Verificar si un ticket tiene un control pendiente", description = "Comprueba si hay una intervención (control) en curso para un ticket dado.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Estado de control pendiente devuelto exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(type = "boolean"))),
			@ApiResponse(responseCode = "404", description = "Ticket no encontrado", content = @Content(mediaType = "text/plain")) })
	@PreAuthorize("hasAnyRole('MANAGER', 'EMPLEADO')")
	@GetMapping("/{idTicket}/ControlPendiente")
	public ResponseEntity<?> controlPendiente(
			@Parameter(description = "ID del ticket a verificar") @PathVariable int idTicket)
			throws TicketNoEncontradoException {
		Ticket ticket = ticketService.buscarTicketPorId(idTicket);
		boolean pendiente = controlService.existeControlPendiente(ticket);
		return ResponseEntity.ok(pendiente);
	}

	@Operation(summary = "Obtener detalles de un control específico", description = "Recupera la información detallada de una intervención (control) por su ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Control encontrado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ControlDTO.class))),
			@ApiResponse(responseCode = "404", description = "Control no encontrado", content = @Content(mediaType = "text/plain")) })
	@PreAuthorize("hasAnyRole('MANAGER', 'EMPLEADO')")
	@GetMapping("/detalle/{idControl}")
	public ResponseEntity<ControlDTO> obtenerControl(
			@Parameter(description = "ID del control a buscar") @PathVariable int idControl)
			throws TicketNoEncontradoException {
		Control control = controlService.buscarControlPorId(idControl)
				.orElseThrow(() -> new TicketNoEncontradoException("Ticket no encontrado"));
		if (control == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(convertirControlDTO(control));
	}

	@Operation(summary = "Obtener detalles de un ticket por ID", description = "Recupera la información detallada de un ticket específico.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ticket encontrado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketDTO.class))),
			@ApiResponse(responseCode = "404", description = "Ticket no encontrado", content = @Content(mediaType = "text/plain")) })
	@PreAuthorize("hasAnyRole('MANAGER', 'EMPLEADO')")
	@GetMapping("/detalleTicket/{idTicket}")
	public ResponseEntity<TicketDTO> obtenerTicket(
			@Parameter(description = "ID del ticket a buscar") @PathVariable int idTicket)
			throws TicketNoEncontradoException {
		Ticket ticket = ticketService.buscarTicketPorId(idTicket);
		if (ticket == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(convertirTicketDTO(ticket));
	}

	@Operation(summary = "Actualizar un control existente", description = "Modifica los detalles de una intervención (control) específica de un ticket.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Control actualizado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ControlDTO.class))),
			@ApiResponse(responseCode = "400", description = "Solicitud inválida (errores de validación o datos)", content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"message\": \"Error al actualizar\", \"errors\": [...]}"))),
			@ApiResponse(responseCode = "404", description = "Control o Ticket no encontrado", content = @Content(mediaType = "text/plain")),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain")) })
	@PreAuthorize("hasAnyRole('MANAGER', 'EMPLEADO')")
	@PutMapping("/{idTicket}/edicion/{idControl}")
	public ResponseEntity<ControlDTO> actualizarControl(
			@Parameter(description = "ID del ticket al que pertenece el control") @PathVariable int idTicket,
			@Parameter(description = "ID del control a actualizar") @PathVariable int idControl,
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos actualizados del control", required = true, content = @Content(schema = @Schema(implementation = ControlDTO.class))) @Valid @RequestBody ControlDTO control,
			@AuthenticationPrincipal UserDetails usuariolog) throws Exception {
		System.out.println("ENTRO AL POST DE EDITAR");
		ControlDTO controlActualizado = controlService.procesarEdicionTicket(control, idControl);
		return ResponseEntity.ok(controlActualizado);
	}

	@Operation(summary = "Tomar un ticket y crear un control inicial",
            description = "Asigna un ticket al empleado logueado creando automáticamente la primera intervención (control). " +
                          "El ticket pasará a estado ABIERTO y el control tendrá acciones predeterminadas y no estará finalizado.")
 	@PreAuthorize("hasAnyRole('MANAGER', 'EMPLEADO')")
	@PostMapping("/{idTicket}/tomarTicket")
	public ResponseEntity<Map<String, Object>> tomarTicketYCrearControlInicial(
			@Parameter(description = "ID del ticket a tomar") @PathVariable int idTicket,
			@AuthenticationPrincipal UserDetails usuariolog) {
		
		Map<String, Object> responseBody = new HashMap<>();
		try {
			Empleado empleadoLogueado = empleadoService.findByEmpleadoNombre(usuariolog.getUsername());
			if (empleadoLogueado == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
			}
			ControlDTO nuevoControl = controlService.tomarTicket(idTicket, empleadoLogueado);
			Ticket ticket = ticketService.buscarTicketPorId(idTicket);
			UsuarioBase usuarioDueño = ticket.getUsuarioCreador();
			if (usuarioDueño != null && usuarioDueño.getEmail() != null) {
                Map<String, Object> emailVariables = new HashMap<>();
                emailVariables.put("nombreUsuario", usuarioDueño.getNombre());
                emailVariables.put("email", usuarioDueño.getEmail());
                emailVariables.put("tituloTicket", ticket.getTitulo());
                emailVariables.put("descripcionTicket", ticket.getDescripcion()); 
                emailVariables.put("ticketId", ticket.getIdTicket());
                emailVariables.put("accionControl", nuevoControl.accion()); 
                emailVariables.put("funcionControl", Funcion.valueOf(nuevoControl.funcion())); 
                emailVariables.put("fechaControl", LocalDate.now().toString());

                System.out.println("📌 Enviando email a: " + usuarioDueño.getEmail());
                emailService.enviarEmailConHtml(
                	usuarioDueño.getEmail(),
                    "Tu ticket #" + ticket.getIdTicket() + " ha sido tomado y se inició un control",
                    "email-control-agregado-template", 
                    emailVariables
                );
                responseBody.put("emailSent", true);
            } else {
                System.err.println("No se pudo enviar email: Usuario creador o email no disponible para ticket #" + idTicket);
                responseBody.put("emailSent", false);
                responseBody.put("emailError", "No se encontró el usuario creador o su email.");
            }

            responseBody.put("message", "¡Ticket #" + idTicket + " tomado y control inicial creado!");
            responseBody.put("control", nuevoControl);
            responseBody.put("ticketId", idTicket); 
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);

		} catch (TicketNoEncontradoException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (IllegalArgumentException e) { 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} catch (Exception e) {
			System.err.println("Error al tomar ticket y crear control inicial: " + e.getMessage());
			if (e.getMessage() != null && e.getMessage().contains("Existe un control pendiente")) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
