package com.grupo7.oo2spring.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.grupo7.oo2spring.models.Ticket;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;
import com.grupo7.oo2spring.services.TicketService;

@Controller
@RequestMapping("/tickets")
public class TicketController {
	
	@Autowired
	private TicketService ticketservice;
	
	@Autowired
	private IUsuarioRepository usuariorepository;
	
	/*@GetMapping("/nuevo")
    public String mostrarFormularioNuevoTicket() {
        return "nuevo_ticket_form"; // Nombre del archivo HTML (sin la extensión .html)
    }*/
	
	@GetMapping("/nuevo-simple")
    public String mostrarFormularioSimple() {
        return "ticket/formulario_simple"; // Nombre del formulario HTML
    }

    @PostMapping("/nuevo-simple")
    public String guardarTicketSimple(
            @RequestParam("titulo") String titulo,
            @RequestParam("descripcion") String descripcion,
            Model model) {
    	model.addAttribute("se creo con exito post");
        // Para este ejemplo simple, vamos a crear un usuario genérico.
        // En una aplicación real, necesitarías obtener el usuario de alguna manera
        // (autenticación, parámetro, etc.).
        Usuario usuarioCreador = usuariorepository.findById(1).orElse(null); // Asumiendo que existe un usuario con ID 1
        
        
        if (usuarioCreador == null) {
            model.addAttribute("mensaje", "Error: No se encontró el usuario creador.");
            return "ticket/exito";
        }

        Ticket nuevoTicket = ticketservice.crearTicket(usuarioCreador, titulo, descripcion);
        model.addAttribute("mensaje", "Ticket creado exitosamente con ID: " + nuevoTicket.getIdTicket());
        return "redirect:/exito"; // Redirigir a la página de éxito
    }

    @GetMapping("/exito")
    public String mostrarExito(Model model) {
        model.addAttribute("mensajeExito", "¡El ticket ha sido creado con éxito!");
        return "ticket/exito"; // Nombre de la página de éxito HTML
    }

	@GetMapping("/ver")
	public ResponseEntity<List<Ticket>> listartickets(){
		List<Ticket> tickets = ticketservice.listatickets();
		return new ResponseEntity<>(tickets, HttpStatus.OK);
	}

}
