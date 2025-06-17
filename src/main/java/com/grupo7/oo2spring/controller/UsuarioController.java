package com.grupo7.oo2spring.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.IEmailTokenRepository;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;
import com.grupo7.oo2spring.services.TicketService;
import com.grupo7.oo2spring.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final IUsuarioRepository usuarioRepository;
    
    private final IEmailTokenRepository emailTokenRepository;

    private final UsuarioService usuarioService;
    
    private final TicketService ticketService;
    
    @GetMapping("/verificar/{id}")
    public String verificarUsuario(@PathVariable int id, Model model) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            model.addAttribute("existeUsuario", true);
            model.addAttribute("nombreUsuario", usuario.getNombre() + " " + usuario.getApellido());
            model.addAttribute("idUsuario", usuario.getIdUsuario());
        } else {
            model.addAttribute("existeUsuario", false);
            model.addAttribute("idUsuario", id);
        }
        return "usuario/verificacion";
    }
    
    
    
    
    



}
