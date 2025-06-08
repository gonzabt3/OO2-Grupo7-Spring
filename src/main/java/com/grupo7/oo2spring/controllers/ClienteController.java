package com.grupo7.oo2spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.grupo7.oo2spring.services.TicketService;
import com.grupo7.oo2spring.services.UsuarioService;

import lombok.RequiredArgsConstructor;

import com.grupo7.oo2spring.models.Ticket;
import com.grupo7.oo2spring.models.Usuario;

@Controller
@RequiredArgsConstructor
public class ClienteController {

    private final UsuarioService usuarioService;

    private final TicketService ticketService;
    

    @GetMapping("/cliente/tickets")
    public String verTicketsCliente(Model model, @AuthenticationPrincipal UserDetails usuariolog) {
        //List<Ticket> tickets = List.of(); 
        String NombreUser = usuariolog.getUsername();
        Usuario user = usuarioService.getUsuarioByUsername(NombreUser);
        //String username = "gonza";
        //Usuario usuario = usuarioService.getUsuarioByUsername(username);
        //List<Ticket> tickets = ticketService.getTicketsByUsuario(usuario);
    	model.addAttribute("usuarioLogueado", usuariolog);
		List<Ticket> tickets = null;
		tickets = ticketService.findByUsuario(user);
        model.addAttribute("tickets", tickets);
        return "cliente-tickets"; 
    }
}