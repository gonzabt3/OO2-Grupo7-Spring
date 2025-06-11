package com.grupo7.oo2spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AuthErrorController {

    @GetMapping("/usuario/error_login")
    public String mostrarErrorLogin(HttpServletRequest request, Model model) {
        Object errorMessage = request.getAttribute("error_message");
        model.addAttribute("mensaje", errorMessage != null ? errorMessage : "Error desconocido");
        return "/error";
    }
}

