package com.grupo7.oo2spring.controller.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo7.oo2spring.exception.TicketNoEncontradoException;
import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Ticket;
import com.grupo7.oo2spring.repositories.IEmpleadoRepository;
import com.grupo7.oo2spring.repositories.ITicketRepository;
import com.grupo7.oo2spring.services.ControlService;
import com.grupo7.oo2spring.services.TicketService;
import com.grupo7.oo2spring.services.UsuarioService;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/control")
@RequiredArgsConstructor
public class ControlRestController {

	private final ITicketRepository ticketRepository;
	private final IEmpleadoRepository empleadoRepository;
	private final TicketService ticketService;
	private final ControlService controlService;
	
	
	@PreAuthorize("hasAnyRole('MANAGER', 'EMPLEADO')")
	@GetMapping("/{idTicket}/nuevo")
	public String crearNuevoControl(@PathVariable int idTicket, Model model) throws Exception {
		Ticket ticket = ticketService.buscarTicketPorId(idTicket);
		model.addAttribute("ticket", ticket);
		model.addAttribute("funciones", Funcion.values());
		boolean pendiente = controlService.existeControlPendiente(ticket);
		model.addAttribute("pendiente",pendiente);
		if(pendiente) {
			//model.addAttribute("errorMessage", "Ya existe un control pendiente");
			ControlDTO controlDTOvacio = new ControlDTO();
			controlDTOvacio.setFinalizado(false);
			model.addAttribute("control", controlDTOvacio);
		}else {
			ControlDTO nuevoControl = new ControlDTO();
			nuevoControl.setFinalizado(false);
			model.addAttribute("control", nuevoControl);
		}
		return "control/crear-control";
	}
	
	@PreAuthorize("hasAnyRole('MANAGER', 'EMPLEADO')")
    @GetMapping("/{idTicket}/editar/{idControl}")
	public String mostrarEdicionDelControl(@PathVariable int idTicket, @PathVariable int idControl, 
			@ModelAttribute("control") ControlDTO control, // Captura los datos del formulario en un objeto Control
            @AuthenticationPrincipal UserDetails usuariolog, Model model) throws Exception {
		Ticket ticket = ticketService.buscarTicketPorId(idTicket);
		Empleado empleadoLogeado = empleadoRepository.findEmpleadoByNombreUsuario(usuariolog.getUsername());
		Control controlEditado = controlService.buscarControlPorId(idControl)
				.orElseThrow(()-> new TicketNoEncontradoException("Control no encontrado"));
		model.addAttribute("ticket", ticket);
		model.addAttribute("IDcontrol", idControl);
		model.addAttribute("funciones", Funcion.values());
		boolean pendiente = controlService.existeControlPendiente(ticket);
		model.addAttribute("pendiente",pendiente);
		if(controlEditado.getEmpleado().getIdEmpleado() != empleadoLogeado.getIdEmpleado()) {
			throw new Exception("Sin permiso para editar");
		}
		ControlDTO controlDTO = new ControlDTO();
		controlDTO.setFuncion(controlEditado.getFuncion());
		controlDTO.setAccion(controlEditado.getAccion());
		controlDTO.setFinalizado(controlEditado.isFinalizado());
		model.addAttribute("control", controlDTO);
		return "control/editar-control";
	}
	
	@PreAuthorize("hasAnyRole('MANAGER', 'EMPLEADO')")
    @GetMapping("/listado-controles/{idTicket}")
    public ResponseEntity<?> mostrarListaControles(@PathVariable int idTicket, @AuthenticationPrincipal UserDetails usuariolog) throws TicketNoEncontradoException {
		Ticket ticket = ticketService.buscarTicketPorId(idTicket);
		// Pasa el ID del usuario logueado para que la plantilla pueda mostrar/ocultar botones de edición
        Empleado empleadoLogeado = empleadoRepository.findEmpleadoByNombreUsuario(usuariolog.getUsername());
        Map<String, Object> response = Map.of(
                "ticket", ticket,
                "controles", ticketService.buscarTicketPorId(idTicket).getProcesos(),
                "currentUserId", empleadoLogeado.getIdEmpleado()
            );
        return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('MANAGER', 'EMPLEADO')")
    @PostMapping("/{idTicket}/nuevo")
    public String procesaCreacionControl(
            @PathVariable int idTicket,
            @Valid @ModelAttribute("control") ControlDTO controlDTO,
            BindingResult resultado,
            @AuthenticationPrincipal UserDetails usuariolog,
            RedirectAttributes redirectAttributes,
            Model model) throws Exception {

        Empleado empleadoLogeado = empleadoRepository.findEmpleadoByNombreUsuario(usuariolog.getUsername());

        // --- Lógica de Manejo de Errores de Validación (DUPLICADA) ---
        if (resultado.hasErrors()) {
            try {
                Ticket ticket = ticketService.buscarTicketPorId(idTicket);
                model.addAttribute("ticket", ticket);
                model.addAttribute("funcionDisponible", Funcion.values());
                model.addAttribute("errorMessage", "Por favor, corrige los errores en el formulario.");
                // Lógica específica para redireccionar a la vista de CREACIÓN con errores
                boolean hasPending = controlService.existeControlPendiente(ticket);
                model.addAttribute("hasPendingControl", hasPending);
                model.addAttribute("hasPermissionToEdit", true);
                return "manager/crear-control";

            } catch (TicketNoEncontradoException e) {
                redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
                return "redirect:/ticket/lista";
            }
        }

        // --- Lógica de Procesamiento de Formulario Válido (llamada al servicio) ---
        try {
            ticketService.tomarTicketConControlInicial(idTicket, empleadoLogeado, controlDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Nueva intervención registrada para Ticket #" + idTicket + ".");
            return "redirect:/ticket/listado-controles/" + idTicket;

        } catch (RuntimeException e) { // Captura excepciones de negocio del servicio
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/ticket/" + idTicket + "/control/new"; // Vuelve al formulario de creación con error
        }
    }
	
	@PreAuthorize("hasAnyRole('MANAGER', 'EMPLEADO')")
	@PostMapping("/{idTicket}/edicion/{idControl}")
	public String procesaEdicionTicket(@PathVariable int idTicket,
									@PathVariable int idControl,
                                    @ModelAttribute("control") ControlDTO control, // Captura los datos del formulario en un objeto Control
                                    @AuthenticationPrincipal UserDetails usuariolog,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes,
                                    Model model) throws Exception {
		System.out.println("ENTRO AL POST DE EDITAR");
		Ticket ticketElegido = ticketService.buscarTicketPorId(idTicket);
		Empleado empleadoAcargo = empleadoRepository.findEmpleadoByNombreUsuario(usuariolog.getUsername());
		//Control ultimoControl = controlService.revisionDeUltimoControl(ticketElegido);
		
		/*if (result.hasErrors()) {
            try {
                Ticket ticket = ticketService.buscarTicketPorId(idTicket);
                model.addAttribute("ticket", ticket);
                model.addAttribute("funcionDisponible", Funcion.values());
                model.addAttribute("errorMessage", "Por favor, corrige los errores en el formulario.");
                // Lógica específica para redireccionar a la vista de EDICIÓN con errores
                model.addAttribute("currentControlId", idControl);
                Optional<Control> existingControlOpt = controlService.buscarControlPorId(idControl);
                boolean hasPerm = existingControlOpt.isPresent() &&
                                  existingControlOpt.get().getEmpleado().getIdEmpleado() == (empleadoAcargo.getIdEmpleado()) &&
                                  !existingControlOpt.get().isFinalizado();
                model.addAttribute("hasPermissionToEdit", hasPerm);
                model.addAttribute("hasPendingControl", false);
                return "manager/editar-control";

            } catch (TicketNoEncontradoException e) {
                redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
                return "redirect:/ticket/lista";
            }
        }*/
		
		controlService.procesarEdicionTicket(control,idControl);
		/*
		if(!ultimoControl.isFinalizado()) {
			if(ultimoControl.getEmpleado().getDni().equalsIgnoreCase(empleadoAcargo.getDni())) {
				controlService.procesarEdicionTicket(control,idControl);
			}
		}else {
			ticketService.tomarTicketConControlInicial(idTicket, empleadoAcargo, control);
		}*/
		return "redirect:/control/lista";
	}
}
