package com.grupo7.oo2spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.grupo7.oo2spring.services.TicketService;
import com.grupo7.oo2spring.services.UsuarioService;

import lombok.RequiredArgsConstructor;

import com.grupo7.oo2spring.models.Ticket;

@Controller
@RequiredArgsConstructor
public class ClienteController {

    private final UsuarioService usuarioService;

    private final TicketService ticketService;
    

    @GetMapping("/cliente/tickets")
    public String verTicketsCliente(Model model) {
        //lista vacia para que no falle al cargar la vista
        // En un caso real, deberías obtener los tickets del usuario autenticado
        List<Ticket> tickets = List.of(); 
        
        // Aquí deberías obtener el usuario autenticado, por ejemplo, desde la sesión
        //String username = "gonza";
        //Usuario usuario = usuarioService.getUsuarioByUsername(username);
        //List<Ticket> tickets = ticketService.getTicketsByUsuario(usuario);
        
        model.addAttribute("tickets", tickets);
        return "cliente-tickets"; 
    }
}