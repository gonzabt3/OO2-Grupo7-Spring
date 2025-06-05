package com.grupo7.oo2spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.grupo7.oo2spring.services.TicketService;
import com.grupo7.oo2spring.services.UsuarioService;
import com.grupo7.oo2spring.models.Ticket;
import com.grupo7.oo2spring.models.Usuario;

@Controller
public class ClienteController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TicketService ticketService;

    public ClienteController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/cliente/tickets")
    public String verTicketsCliente(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String prioridad,
            Model model) {
        
        Usuario usuario = usuarioService.getUsuarioByUsername("123"); // Usuario autenticado
        List<Ticket> tickets = ticketService.getTicketsByUsuario(usuario);

        // Filtrar por título
        if (titulo != null && !titulo.isEmpty()) {
            tickets = tickets.stream()
                    .filter(ticket -> ticket.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                    .toList();
        }

        // Filtrar por estado
        if (estado != null && !estado.isEmpty()) {
            tickets = tickets.stream()
                    .filter(ticket -> ticket.getEstado().toString().equalsIgnoreCase(estado))
                    .toList();
        }

        // Filtrar por prioridad
        if (prioridad != null && !prioridad.isEmpty()) {
            tickets = tickets.stream()
                    .filter(ticket -> ticket.getPrioridad().toString().equalsIgnoreCase(prioridad))
                    .toList();
        }

        model.addAttribute("tickets", tickets);
        return "cliente-tickets";
    }
}