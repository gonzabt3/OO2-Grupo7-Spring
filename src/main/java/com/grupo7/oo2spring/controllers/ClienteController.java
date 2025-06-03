package com.grupo7.oo2spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClienteController {

    @GetMapping("/cliente/tickets")
    public String verTicketsCliente(Model model) {
        return "cliente-tickets"; 
    }
}