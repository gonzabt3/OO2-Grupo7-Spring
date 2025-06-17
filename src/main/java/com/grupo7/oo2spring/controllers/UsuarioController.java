package com.grupo7.oo2spring.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grupo7.oo2spring.models.EmailToken;
import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Rol;
import com.grupo7.oo2spring.models.Ticket;
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
    
    @GetMapping("/misDatos")
    public String verificarUsuario(Model model, @AuthenticationPrincipal UserDetails usuariolog) {
    	String nombre = usuariolog.getUsername();
        Optional<Usuario> usuarioOptional = usuarioRepository.findByNombreUsuario(nombre);
        Usuario usuariologueado = usuarioOptional.get();
        model.addAttribute("usuariologueado", usuariologueado);
        return "usuario/datos_usuario";
    }
   
}
