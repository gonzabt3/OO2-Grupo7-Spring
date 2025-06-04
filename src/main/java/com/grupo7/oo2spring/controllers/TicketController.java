package com.grupo7.oo2spring.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grupo7.oo2spring.dto.TicketDTO;
import com.grupo7.oo2spring.dto.UsuarioDTO;
import com.grupo7.oo2spring.models.Estado;
import com.grupo7.oo2spring.models.Prioridad;
import com.grupo7.oo2spring.models.Ticket;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.ITicketRepository;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;
import com.grupo7.oo2spring.services.TicketService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private ITicketRepository ticketRepository;
    
    @Autowired
    private TicketService ticketService;

    @GetMapping("/formulario_simple")
    public String mostrarFormulario(Model model) {
        model.addAttribute("ticket", new TicketDTO());
        return "ticket/formulario_simple";
    }

    @PostMapping("/crear")
    public String crearTicket(@ModelAttribute("ticket") TicketDTO ticketDTO, Model model) {
    	try {
        Usuario user = usuarioRepository.findById(ticketDTO.getUsuarioCreador().getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Ticket nuevoTicket = new Ticket();
        nuevoTicket.setUsuarioCreador(user);
        nuevoTicket.setTitulo(ticketDTO.getTitulo());
        nuevoTicket.setDescripcion(ticketDTO.getDescripcion());
        nuevoTicket.setEstado(Estado.PENDIENTE);
        nuevoTicket.setPrioridad(Prioridad.MEDIA);

        ticketRepository.save(nuevoTicket);

        model.addAttribute("mensaje", "Ticket creado con éxito");
        return "ticket-creado"; 
    	} catch (RuntimeException ex) {
    	    model.addAttribute("error", ex.getMessage());
    	    return "formulario-ticket";
    	}
    }
 

    @PostMapping("/nuevo-simple")
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
    }

    @GetMapping("/exito")
    public String mostrarExito(Model model) {
        model.addAttribute("mensajeExito", "¡El ticket ha sido creado con éxito!");
        return "ticket/exito";
    }

	@GetMapping("/ver")
	public ResponseEntity<List<Ticket>> listartickets(){
		List<Ticket> tickets = ticketRepository.findAll();
		return new ResponseEntity<>(tickets, HttpStatus.OK);
    
	}
}

