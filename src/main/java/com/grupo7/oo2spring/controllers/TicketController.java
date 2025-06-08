package com.grupo7.oo2spring.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grupo7.oo2spring.dto.ControlDTO;
import com.grupo7.oo2spring.dto.TicketDTO;
import com.grupo7.oo2spring.models.Control;
import com.grupo7.oo2spring.models.Ticket;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.repositories.ITicketRepository;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;
import com.grupo7.oo2spring.services.TicketService;
import com.grupo7.oo2spring.services.UsuarioService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/ticket")
public class TicketController {

	private final IUsuarioRepository usuarioRepository;
	
	private final ITicketRepository ticketRepository;
    
    private final TicketService ticketService;

    private final UsuarioService usuarioService;
    

    @GetMapping("/formulario_simple")
    public String mostrarFormulario(Model model) {
        model.addAttribute("ticket", new TicketDTO());
        return "ticket/formulario_simple";
    }

    @PostMapping("/crear")
    public String crearTicket(@ModelAttribute("ticket") TicketDTO ticketDTO, Model model,
    		@AuthenticationPrincipal UserDetails usuariolog) {
    	
    	if(usuariolog == null) {
    		 model.addAttribute("mensaje", "Error con el Usuario");
    		return "redirect:/panel";
    	}
    	
    	String nombreDelUsuarioEnSesion = usuariolog.getUsername();
    	
    	Usuario usuarioCreador = usuarioService.getUsuarioByUsername(nombreDelUsuarioEnSesion);
    	try {
        	ticketService.crearTicket(ticketDTO, usuarioCreador); 
        	model.addAttribute("successMessage", "¡Ticket exitoso! Ya puedes iniciar sesión.");
            return "ticket/exito";
        } catch (RuntimeException e) {
        	System.err.println("Error al registrar usuario: " + e.getMessage());
            model.addAttribute("error", e.getMessage());
            model.addAttribute("ticket", ticketDTO); // Para que los datos no se borren al volver al form
            return "formulario-ticket";
        }
    }
 

    /*@PostMapping("/nuevo-simple")
    public String guardarTicketSimple(
            @RequestParam("titulo") String titulo,
            @RequestParam("descripcion") String descripcion,
            Model model) {
    	model.addAttribute("se creo con exito post");
        Usuario usuarioCreador = usuarioRepository.findById(1).orElse(null); // Asumiendo que existe un usuario con ID 1
        
        
        if (usuarioCreador == null) {
            model.addAttribute("mensaje", "Error: No se encontró el usuario creador.");
            return "ticket/exito";
        }

        Ticket nuevoTicket = ticketService.crearTicket(usuarioCreador, titulo, descripcion);
        model.addAttribute("mensaje", "Ticket creado exitosamente con ID: " + nuevoTicket.getIdTicket());
        return "redirect:/exito"; // Redirigir a la página de éxito
    }*/

    @GetMapping("/exito")
    public String mostrarExito(Model model) {
        model.addAttribute("mensajeExito", "¡El ticket ha sido creado con éxito!");
        return "ticket/exito";
    }

	@GetMapping("/lista")
	@PreAuthorize("hasRole('EMPLEADO')")
	public String listartickets(Model model){
		List<Ticket> tickets = ticketRepository.findAll();
		model.addAttribute("tickets", tickets);
		return "ticket/lista_tickets";
    }
	
	@GetMapping("/{idTicket}/tomarTicket")
	public String tomarTicket(@PathVariable int idTicket, Model model) {
		try {
			Ticket ticket = ticketService.getByIdTicket(idTicket);
//			if (ticket.getEstado().equals("Cerrado") || ticket.getEstado().equals("Resuelto")) {
//	            model.addAttribute("errorMessage", "El ticket #" + idTicket + " no puede ser tomado en su estado actual.");
//	            return "redirect:/ticket/lista";
//	       }
			model.addAttribute("ticket",ticket);
			model.addAttribute("control", new Control());
			return "manager/toma-ticket";
		}catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/ticket/lista";
        }
	}
	
	@PostMapping("/{idTicket}/tomar")
    public String processTakeTicket(@PathVariable int idTicket,
                                    @ModelAttribute("control") ControlDTO control, // Captura los datos del formulario en un objeto Control
                                    @AuthenticationPrincipal UserDetails usuariolog,
                                    Model model) throws Exception {
		String nombreDelUsuarioEnSesion = usuariolog.getUsername();
    	Usuario usuarioCreador = usuarioService.getUsuarioByUsername(nombreDelUsuarioEnSesion);
        try {
            // Llama al servicio con los datos del Control inicial
            ticketService.tomarTicketConControlInicial(idTicket, usuarioCreador, control);
            model.addAttribute("successMessage", "¡Ticket #" + idTicket + " tomado y gestión iniciada!");
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Error al tomar el ticket #" + idTicket + ": " + e.getMessage());
            // Si hay un error, redirigir al formulario de toma con el ticket para que pueda intentar de nuevo
            return "redirect:/ticket/" + idTicket + "/tomarTicket";
        }
        return "redirect:/ticket/lista"; // Redirige al dashboard o a la vista de detalle del ticket recién tomado
    }
	
	@GetMapping("/{idTicket}/detail")
    public String viewTicketDetail(@PathVariable int idTicket, Model model, @AuthenticationPrincipal UserDetails usuariolog) {
        try {
            Ticket ticketDetail = ticketService.getByIdTicket(idTicket);

            model.addAttribute("ticketDetail", ticketDetail);
            model.addAttribute("controlCreationDTO", new ControlDTO()); // Para el formulario de agregar controles
            return "ticket/ticket-detail";
        } catch (RuntimeException e) {
        	model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/ticket/lista_tickets";
        }
    }
	
	@PostMapping("/{idTicket}/add-control")
    public String addControl(@PathVariable int idTicket,
                             @Valid @ModelAttribute("controlCreationDTO") ControlDTO control,
                             BindingResult result,
                             @RequestParam(defaultValue = "false") boolean finalizado,
                             @AuthenticationPrincipal UserDetails usuariolog,
                             Model model) throws Exception {
//        if (result.hasErrors()) {
//            try {
//                model.addAttribute("ticketDetail", ticketService.getByIdTicket(idTicket));
//            } catch (RuntimeException e) {
//            	model.addAttribute("errorMessage", "Error al cargar detalles del ticket para reintentar: " + e.getMessage());
//                return "redirect:/empleado/tickets/all";
//            }
//            model.addAttribute("errorMessage", "Por favor, corrija los errores en el formulario.");
//            return "empleado/ticket-detail"; // Vuelve a la misma vista de detalle
//        }

		String nombreDelUsuarioEnSesion = usuariolog.getUsername();
		Usuario usuarioCreador = usuarioService.getUsuarioByUsername(nombreDelUsuarioEnSesion);
        Empleado empleado = (Empleado) usuariolog;
        try {
            ticketService.agregarControlATicket(idTicket, empleado, control, finalizado);
            model.addAttribute("successMessage", "Control agregado con éxito.");
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Error al agregar control: " + e.getMessage());
        }
        return "redirect:/ticket/" + idTicket + "/detail";
    }
}

