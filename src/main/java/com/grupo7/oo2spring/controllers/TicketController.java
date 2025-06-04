package com.grupo7.oo2spring.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private ITicketRepository ticketRepository;

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("ticket", new TicketDTO());
        return "formulario-ticket";
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
        return "ticket-creado"; // Este es el HTML que deberías crear
    	} catch (RuntimeException ex) {
    	    model.addAttribute("error", ex.getMessage());
    	    return "formulario-ticket"; // Volvés al formulario con mensaje de error
    	}
    }
}

