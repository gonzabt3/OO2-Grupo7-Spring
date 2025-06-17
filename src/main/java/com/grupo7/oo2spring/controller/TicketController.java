package com.grupo7.oo2spring.controller;

import java.util.List;
import java.util.Optional;

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

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grupo7.oo2spring.dto.ControlDTO;
import com.grupo7.oo2spring.dto.TicketDTO;
import com.grupo7.oo2spring.exception.TicketNoEncontradoException;
import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.models.Control;
import com.grupo7.oo2spring.models.Ticket;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Estado;
import com.grupo7.oo2spring.models.Funcion;
import com.grupo7.oo2spring.models.Prioridad;
import com.grupo7.oo2spring.repositories.ITicketRepository;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;
import com.grupo7.oo2spring.services.EmpleadoService;
import com.grupo7.oo2spring.services.TicketService;
import com.grupo7.oo2spring.services.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/ticket")
public class TicketController {

	private final IUsuarioRepository usuarioRepository;
	
	private final ITicketRepository ticketRepository;
    
    private final TicketService ticketService;

    private final EmpleadoService empleadoService;
    
    private final UsuarioService usuarioService;
    

    @GetMapping("/formulario_simple")
    public String mostrarFormulario(Model model) {
        model.addAttribute("ticket", new TicketDTO());
        return "ticket/formulario_simple";
    }

    @PostMapping("/crear")
    public String crearTicket(@ModelAttribute("ticket") TicketDTO ticketDTO, RedirectAttributes redirectAttributes,
                              @AuthenticationPrincipal UserDetails usuariolog) {
        System.out.println("CONTROLADOR: Recibido POST /crear con ticketDTO: " + ticketDTO);

        if (usuariolog == null) {
            System.out.println("CONTROLADOR: Usuario no autenticado");
            redirectAttributes.addFlashAttribute("mensaje", "Error con el Usuario");
            return "redirect:/panel";
        }

        String nombreDelUsuarioEnSesion = usuariolog.getUsername();
        System.out.println("CONTROLADOR: Usuario en sesión: " + nombreDelUsuarioEnSesion);

        Usuario usuarioCreador = usuarioService.getUsuarioByNombreUsuario(nombreDelUsuarioEnSesion);
        System.out.println("CONTROLADOR: Usuario creador obtenido: " + usuarioCreador);

        try {
            Ticket ticketCreado = ticketService.crearTicket(ticketDTO, usuarioCreador); 
            System.out.println("CONTROLADOR: Ticket creado con ID: " + ticketCreado.getIdTicket());
            redirectAttributes.addFlashAttribute("successMessage", "¡Ticket exitoso! Ya puedes iniciar sesión.");
            return "redirect:/ticket/exito";
        } catch (RuntimeException e) {
            System.err.println("CONTROLADOR: Error al registrar ticket: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("ticket", ticketDTO);
            return "redirect:/formulario-ticket";
        }
    }
 

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
	
	@GetMapping("/listaArea")
	@PreAuthorize("hasRole('EMPLEADO')")
	public String listarticketsPorArea(Model model, @AuthenticationPrincipal UserDetails usuariolog){
		model.addAttribute("usuarioLogueado", usuariolog);
		List<Ticket> tickets = null;
		//Empleado usuario = (Empleado) usuarioService.getUsuarioByUsername(usuariolog.getUsername());
		Empleado empleadoOpt = empleadoService.findByEmpleadoNombre(usuariolog.getUsername());
		if(empleadoOpt.getArea() != null) {
			tickets = ticketService.findByArea(empleadoOpt.getArea());
			model.addAttribute("message", "Mostrando solo tickets de su área: " + empleadoOpt.getArea());
			model.addAttribute("tickets", tickets);
		}else {
			model.addAttribute("message", "No existen tickets asignados a su Area ");
		}
		model.addAttribute("tickets", tickets);
		model.addAttribute("usuarioLogueado", empleadoOpt);
		model.addAttribute("estados", Estado.values()); // Esto obtiene un array de todas las constantes de tu enum Estado
        model.addAttribute("prioridades", Prioridad.values());
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
    public String procesaTomarTicket(@PathVariable int idTicket,
                                    @ModelAttribute("control") ControlDTO control, // Captura los datos del formulario en un objeto Control
                                    @AuthenticationPrincipal UserDetails usuariolog,
                                    Model model) throws Exception {
		String nombreDelUsuarioEnSesion = usuariolog.getUsername();
    	Empleado empleadoLogeado = empleadoService.findByEmpleadoNombre(nombreDelUsuarioEnSesion);
    	try {
            ticketService.tomarTicketConControlInicial(idTicket, empleadoLogeado, control);
            model.addAttribute("successMessage", "¡Ticket #" + idTicket + " tomado y gestión iniciada!");
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Error al tomar el ticket #" + idTicket + ": " + e.getMessage());
            return "redirect:/ticket/" + idTicket + "/tomarTicket";
        }
        return "redirect:/ticket/lista";
    }
	
	@GetMapping("/{idTicket}/detail")
    public String DetalleTicket(@PathVariable int idTicket, Model model, @AuthenticationPrincipal UserDetails usuariolog) {
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
	
	@GetMapping("/sinasignar")
    //@PreAuthorize("hasRole('MANAGER')") // Solo un manager puede ver y asignar tickets sin área
    public String ticketSinArea(Model model) {
        try {
            List<Ticket> ticketSinArea = ticketService.findByAreaIsNull();
            model.addAttribute("tickets", ticketSinArea);
            model.addAttribute("areas", Area.values());
            return "ticket/ticket-sin-area"; 
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/ticket/ticket-sin-area"; 
        }
    }
	
	@PostMapping("/{idTicket}/asignarArea")
    //@PreAuthorize("hasRole('MANAGER')") // Solo un manager puede asignar área
    public String asigarUnArea(@PathVariable int idTicket,
                                     @RequestParam("area") Area area,
                                     Model model) throws TicketNoEncontradoException{
		ticketService.asignarAreaTicket(idTicket, area);
		model.addAttribute("successMessage", "¡Área '" + area.name() + "' asignada al ticket #" + idTicket + " con éxito!");
		return "redirect:/ticket/sinasignar";
    }
	
	@GetMapping("/tickets")
	public String verTicketsUsuario(Model model, @AuthenticationPrincipal UserDetails userDetails) {
	    if (userDetails == null) {
	        return "redirect:/login";
	    }

	    String username = userDetails.getUsername();
	    Usuario usuario = usuarioService.getUsuarioByNombreUsuario(username);

	    List<Ticket> tickets = ticketService.getTicketsByUsuario(usuario.getIdUsuario());
	    model.addAttribute("tickets", tickets);

	    return "ticket/usuario-tickets"; // Vista con la tabla de tickets
	}
	
	@PostMapping("/{idTicket}/cambiarPrioridad")
	public String cambiarPrioridad(@PathVariable int idTicket, @RequestParam("prioridad")Prioridad prioridad, Model model) throws Exception {
		ticketService.asignarPrioridad(idTicket, prioridad);
		return "redirect:/ticket/listaArea";
	}
	
	@PostMapping("/{idTicket}/cambiarEstado")
	public String cambiarEstado(@PathVariable int idTicket, @RequestParam("estado")Estado estado, Model model) throws Exception {
		ticketService.asignarEstado(idTicket, estado);
		return "redirect:/ticket/listaArea";
	}
}

