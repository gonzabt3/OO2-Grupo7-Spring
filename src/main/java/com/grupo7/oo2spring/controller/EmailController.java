package com.grupo7.oo2spring.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.grupo7.oo2spring.models.MensajeContacto;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.IMensajeRepository;
import com.grupo7.oo2spring.security.UsuarioDetails;
import com.grupo7.oo2spring.services.EmailService;
import com.grupo7.oo2spring.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/contacto")

public class EmailController {
	
	  private final EmailService emailService;
	  private final UsuarioService usuarioService;
	  private final IMensajeRepository mensajeRepository;
	  
	// Dirección de remitente que usarás, EMAIL_USERNAME de Gmail
	    @Value("${EMAIL_TEST}") 
	    private String emailSenderFrom;

	  @GetMapping("/formulario-contacto")
	  public String mostrarFormularioContacto(@RequestParam(value = "exito", required = false) String exito, Model model) {
		  if (exito != null) {
		        model.addAttribute("exito", true);
		    }
		  
	      return "contacto/formulario-contacto"; 
	  }
    
    public String obtenerEmailUsuarioAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()) {
            Object principal = auth.getPrincipal();

            if (principal instanceof UsuarioDetails usuarioDetails) {
                return usuarioDetails.getEmail(); //
            }
        }

        return null; // No autenticado
    }
    
    @GetMapping("/enviar")
    public String redirectToFormulario() {
    	
        return "redirect:/contacto/formulario-contacto";
    }
    
    
    @PostMapping("/enviar")
    public String enviarContacto(@RequestParam("mensaje") String mensaje, Principal principal) {
    	System.out.println("👤 principal.getName(): " + principal.getName());
        Usuario usuario = usuarioService.getUsuarioByNombreUsuario(principal.getName());

        // 1. Guardar en la BD
        MensajeContacto nuevo = new MensajeContacto();
        nuevo.setMensaje(mensaje);
        nuevo.setUsuario(usuario);
        mensajeRepository.save(nuevo);

        // 2. Enviar por email
        Map<String, Object> variables = new HashMap<>();
        variables.put("nombreUsuario", principal.getName());
        variables.put("email", usuario.getEmail());
        variables.put("mensaje", mensaje);
        

        emailService.enviarEmailConHtml(usuario.getEmail(), "Nuevo mensaje de contacto", "email-template", variables);
        
        System.out.println("🔔 Enviando email a: " + emailSenderFrom);

        return "redirect:/contacto/formulario-contacto?exito";
    }
    
}