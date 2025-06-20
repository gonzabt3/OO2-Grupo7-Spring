package com.grupo7.oo2spring.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.grupo7.oo2spring.exception.TicketCreacionException;
import com.grupo7.oo2spring.exception.TicketNoEncontradoException;
import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.models.Control;
import com.grupo7.oo2spring.models.Ticket;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.models.UsuarioBase;
import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Estado;
import com.grupo7.oo2spring.models.Prioridad;
import com.grupo7.oo2spring.repositories.ITicketRepository;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;
import com.grupo7.oo2spring.security.UsuarioDetails;
import com.grupo7.oo2spring.services.EmailService;
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
    
    private final EmailService emailService;
    

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
		return "ticket/lista_tickets";
    }
	
	@PreAuthorize("hasAnyRole('MANAGER', 'EMPLEADO')")
	@GetMapping("/{idTicket}/tomarTicket")
	public String tomarTicket(@PathVariable int idTicket, Model model) throws TicketNoEncontradoException {
		try {
			Ticket ticket = ticketService.buscarTicketPorId(idTicket);
			model.addAttribute("ticket",ticket);
			model.addAttribute("control", new Control());
			return "manager/toma-ticket";
		}catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/ticket/lista";
        }
	}
	
	@PreAuthorize("hasAnyRole('MANAGER', 'EMPLEADO')")
	@PostMapping("/{idTicket}/tomar")
    public String processTakeTicket(@PathVariable int idTicket,
                                    @ModelAttribute("control") ControlDTO control, // Captura los datos del formulario en un objeto Control
                                    @AuthenticationPrincipal UserDetails usuariolog,
                                    Model model) throws Exception, TicketNoEncontradoException {
		System.out.println("➡️ Entró al controlador tomarTicketConControlInicial");
		String nombreDelUsuarioEnSesion = usuariolog.getUsername();
    	Empleado empleadoLogeado = empleadoService.findByEmpleadoNombre(nombreDelUsuarioEnSesion);
    	try {

            ticketService.tomarTicketConControlInicial(idTicket, empleadoLogeado, control);
            

            
            Ticket ticket = ticketService.buscarTicketPorId(idTicket);
		    Usuario usuarioDueño = ticket.getUsuarioCreador(); // asumimos que Ticket tiene un Usuario asociado
		    
            System.out.println(usuarioDueño.getEmail());

		    // ✅ Armar variables para el template
		    Map<String, Object> variables = new HashMap<>();
		    variables.put("nombreUsuario", usuarioDueño.getNombre());
		    variables.put("email",usuarioDueño.getEmail());
		    variables.put("tituloTicket", ticket.getTitulo());
		    variables.put("descripcionControl", ticket.getDescripcion());
		    variables.put("ticketId", ticket.getIdTicket());
		    variables.put("accionControl", control.getAccion());
		    variables.put("fechaControl", LocalDate.now().toString());
		    //variables.put("urlDetalle", "http://localhost:8080/ticket/tickets");

		    System.out.println("📌 emailService es: " + emailService);
		    
		    
		    // ✅ Enviar el email con plantilla
		    emailService.enviarEmailConHtml(
		        usuarioDueño.getEmail(),
		        "Se actualizó tu ticket #" + ticket.getIdTicket(), "email-control-agregado-template",
		        variables
		    );

		    model.addAttribute("successMessage", "Control agregado con éxito y correo enviado.");
            model.addAttribute("successMessage", "¡Ticket #" + idTicket + " tomado y gestión iniciada!");
        } catch (TicketCreacionException e) {
            model.addAttribute("errorMessage", "Error al tomar el ticket #" + idTicket + ": " + e.getMessage());
            // Si hay un error, redirie al formulario de toma con el ticket para que pueda intentar de nuevo
            return "redirect:/ticket/" + idTicket + "/tomarTicket";
        }
        return "redirect:/ticket/lista"; // Redirige al dashboard o a la vista de detalle del ticket recién tomado
    }
	
	@GetMapping("/{idTicket}/detail")
    public String DetalleTicket(@PathVariable int idTicket, Model model, @AuthenticationPrincipal UserDetails usuariolog) throws TicketNoEncontradoException {
        Ticket ticketDetail = ticketService.buscarTicketPorId(idTicket);
        model.addAttribute("ticketDetail", ticketDetail);
        model.addAttribute("controlCreationDTO", new ControlDTO()); // Para el formulario de agregar controles
        return "ticket/ticket-detail";
    }
	
	
	@PreAuthorize("hasAnyRole('MANAGER')")
	@GetMapping("/lista")
    public String ticketsDelSistema(Model model, @AuthenticationPrincipal UserDetails usuariolog) {
		UsuarioDetails usuarioDetails = (UsuarioDetails)usuariolog;
		UsuarioBase empleado = usuarioDetails.getUsuario();
        try {
            List<Ticket> tickets = ticketRepository.findAll();
            model.addAttribute("tickets", tickets);
            model.addAttribute("rol",empleado.getRol());
            model.addAttribute("areas", Area.values());
            model.addAttribute("estados", Estado.values());
            model.addAttribute("prioridades", Prioridad.values());
            return "ticket/ticket_del_sistema"; 
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/ticket/ticket_del_sistema"; 
        }
    }
	

	@PostMapping("/{idTicket}/asignarArea")
	@PreAuthorize("hasAnyRole('MANAGER')")
	public String asigarUnArea(@PathVariable int idTicket,
            @RequestParam("area") Area area,
            RedirectAttributes redirectAttributes) throws TicketNoEncontradoException {
		ticketService.asignarAreaTicket(idTicket, area);
		redirectAttributes.addFlashAttribute("successMessage", "¡Área '" + area.name() + "' asignada al ticket #" + idTicket + " con éxito!");
		return "redirect:/ticket/sinasignar";
	}
	
	@PreAuthorize("hasAnyRole('USER')")
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
	
	@PreAuthorize("hasAnyRole('MANAGER', 'EMPLEADO')")
	@PostMapping("/{idTicket}/cambiarPrioridad")
	public String cambiarPrioridad(@PathVariable int idTicket, @RequestParam("prioridad")Prioridad prioridad, Model model) throws Exception {
		ticketService.asignarPrioridad(idTicket, prioridad);
		return "redirect:/ticket/lista";
	}
	@PreAuthorize("hasAnyRole('MANAGER', 'EMPLEADO')")
	@PostMapping("/{idTicket}/cambiarEstado")
	public String cambiarEstado(@PathVariable int idTicket, @RequestParam("estado")Estado estado, Model model) throws Exception {
		ticketService.asignarEstado(idTicket, estado);
		return "redirect:/ticket/lista";
	}
	
	//Para testear la excepcion
    @GetMapping("/probar-exception")
    public String probarError() throws TicketNoEncontradoException {
        throw new TicketNoEncontradoException("No se encontró el ticket solicitado.");
    }
}

