package com.grupo7.oo2spring.controllers;

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

    @GetMapping("/registrar")
    public String mostrarFormularioRegistro() {
        return "usuario/registro_form";// Nombre del formulario HTML
    }

    //TODO: Duplicado? No lo hace ya RegistroController
    @PostMapping("/registrar")
    public String registrarUsuario(
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("dni") String dni,
            @RequestParam("email") String email,
            @RequestParam("nombreUsuario") String nombreUsuario,
            @RequestParam("contraseña") String contraseña,
            Model model) {
        Usuario usuarioGuardado = usuarioService.guardarUsuario(nombre, apellido, dni, email, nombreUsuario, contraseña);
        model.addAttribute("mensaje", "Usuario registrado exitosamente con ID: " + usuarioGuardado.getIdUsuario());
        return "usuario/registro_exito";
    }
    
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
